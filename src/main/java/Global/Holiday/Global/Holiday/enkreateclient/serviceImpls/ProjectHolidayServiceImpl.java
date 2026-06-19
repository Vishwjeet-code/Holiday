package Global.Holiday.Global.Holiday.enkreateclient.serviceImpls;

import Global.Holiday.Global.Holiday.admin.dtos.CityDto;
import Global.Holiday.Global.Holiday.admin.entities.City;
import Global.Holiday.Global.Holiday.admin.entities.Country;
import Global.Holiday.Global.Holiday.admin.entities.Year;
import Global.Holiday.Global.Holiday.admin.entities.date;
import Global.Holiday.Global.Holiday.admin.repositories.*;
import Global.Holiday.Global.Holiday.enkreateclient.entities.Approval;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.CopyGlobalHolidayToProjectHolidayDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.ProjectHolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.response.projectHolidayResponseDto;


import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.HolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.RequestDateDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.RequestHoldiayDTO;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.CountryResponsDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.DateResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.ResponseHolidayDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.YearResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.entities.Holiday;
import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectHoliday;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.ProjectworkingDaysRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.*;
import Global.Holiday.Global.Holiday.enkreateclient.services.ProjectHolidayService;
import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProjectHolidayServiceImpl implements ProjectHolidayService {


    private final ProjectDetailsRepository projectDetailsRepository;
    private final HolidayRepository holidayRepository;
    private final ProjectHolidayRepository projectHolidayRepository;
    private final CountryRepository countryRepository;
    private final YearRepository yearRepository;
    private final DateRepository dateRepository;
    private final DayRepository dayRepository;
    private final QuarterRepository quarterRepository;
    private final MonthRepository monthRepository;
    private final WeekRepository weekRepository;
    private final CityRepository cityRepository;
    private final IndustryRepository industryRepository;
    private final ApprovalRepositories statusMasterRepositories;
    private final ProjectWorkingDaysRepository projectWorkingDaysRepository;

    public ProjectHolidayServiceImpl(HolidayRepository holidayRepository, ProjectDetailsRepository projectDetailsRepository, HolidayRepository holidayRepository1, ProjectHolidayRepository projectHolidayRepository, CountryRepository countryRepository, YearRepository yearRepository, DateRepository dateRepository, DayRepository dayRepository, QuarterRepository quarterRepository, MonthRepository monthRepository, WeekRepository weekRepository, CityRepository cityRepository, IndustryRepository industryRepository, ApprovalRepositories statusMasterRepositories, ApprovalRepositories statusMasterRepositories1, ProjectWorkingDaysRepository projectWorkingDaysRepository) {
        this.projectDetailsRepository = projectDetailsRepository;
        this.holidayRepository = holidayRepository1;
        this.projectHolidayRepository = projectHolidayRepository;
        this.countryRepository = countryRepository;
        this.yearRepository = yearRepository;
        this.dateRepository = dateRepository;
        this.dayRepository = dayRepository;
        this.quarterRepository = quarterRepository;
        this.monthRepository = monthRepository;
        this.weekRepository = weekRepository;
        this.cityRepository = cityRepository;
        this.industryRepository = industryRepository;
        this.statusMasterRepositories = statusMasterRepositories1;

        this.projectWorkingDaysRepository = projectWorkingDaysRepository;
    }


    @Override
    public void saveProjectHoliday(CopyGlobalHolidayToProjectHolidayDto request) {
        try {
            // ✅ fetch cityId + industryId from ProjectDetails table
            ProjectDetails project = projectDetailsRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException(
                            "Project not found with id: " + request.getProjectId()));

            Long cityId = project.getProjectCityId();
            Long industryId = project.getIndustryId();

            for (Long holidayId : request.getHolidayIds()) {

                // ✅ validate holiday belongs to correct country + year
                Holiday holiday = holidayRepository
                        .findByHolidayIdAndCountryIdAndYearId(
                                holidayId,
                                request.getCountryId(),
                                request.getYearId())
                        .orElseThrow(() -> new RuntimeException(
                                "Holiday not found for given country and year, id: " + holidayId));

                // ✅ duplicate check
                boolean exists = projectHolidayRepository
                        .existsByCityIdAndIndustryIdAndHolidayId(cityId, industryId, holidayId);
                if (exists) {
                    throw new RuntimeException(
                            "Holiday already linked to this project: " + holiday.getHolidayName());
                }
                Approval savedStatus = statusMasterRepositories
                        .findByStatus("Saved")
                        .orElseThrow(() -> new RuntimeException("Status 'Saved' not found"));

                // ✅ save
                ProjectHoliday ph = new ProjectHoliday();
                ph.setProjectId(request.getProjectId());
                ph.setCityId(cityId);
                ph.setIndustryId(industryId);
                ph.setHolidayId(holidayId);
                ph.setCreatedBy(request.getCreatedBy());
                ph.setCreateDate(LocalDateTime.now());
                ph.setStatusId(savedStatus.getApproval_Status_Id());  // ✅ Saved status by default
                projectHolidayRepository.save(ph);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<projectHolidayResponseDto> getProjectHolidays(Long projectId, Long countryId, Long yearId) {



            ProjectDetails project = projectDetailsRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            Long cityId = project.getProjectCityId();
            Long industryId = project.getIndustryId();

            // ================= PROJECT HOLIDAY =================

            List<ProjectHoliday> projectHolidayList =
                    projectHolidayRepository.findByCityIdAndIndustryId(cityId, industryId);

            if (projectHolidayList.isEmpty()) {
                return new ArrayList<>();
            }

            // ================= MAP holidayId -> statusId =================

            Map<Long, Long> holidayIdToStatusId = projectHolidayList.stream()
                    .collect(Collectors.toMap(
                            ProjectHoliday::getHolidayId,
                            ProjectHoliday::getStatusId,
                            (existing, replacement) -> existing
                    ));

            // ================= FETCH ALL STATUSES IN ONE QUERY =================

            Set<Long> statusIds = projectHolidayList.stream()
                    .map(ProjectHoliday::getStatusId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Map<Long, Approval> statusMap = statusMasterRepositories.findAllById(statusIds)
                    .stream()
                    .collect(Collectors.toMap(Approval::getApproval_Status_Id, s -> s));

            // ================= HOLIDAY IDS =================

            Set<Long> holidayIds = projectHolidayList.stream()
                    .map(ProjectHoliday::getHolidayId)
                    .collect(Collectors.toSet());

            // ================= FETCH HOLIDAYS =================

            List<Holiday> holidayList =
                    holidayRepository.findByHolidayIdInAndCountryIdAndYearId(holidayIds, countryId, yearId);

            if (holidayList.isEmpty()) {
                return new ArrayList<>();
            }

            // ================= GROUP BY DATE =================

            Map<Long, List<Holiday>> groupedByDate = holidayList.stream()
                    .collect(Collectors.groupingBy(Holiday::getDateId));

            List<DateResponseDto> dateResponseDtos = new ArrayList<>();

            for (Map.Entry<Long, List<Holiday>> entry : groupedByDate.entrySet()) {

                Long dateId = entry.getKey();
                List<Holiday> holidays = entry.getValue();
                Holiday firstHoliday = holidays.get(0);

                // ================= DATE =================

                date dbDate = dateRepository.findById(dateId)
                        .orElseThrow(() -> new RuntimeException("Date not found"));

                DateResponseDto dateResponseDto = new DateResponseDto();
                dateResponseDto.setDateId(dateId);
                dateResponseDto.setDate(dbDate.getDate());

                // ================= DAY =================

                String dayName = dayRepository.findById(dbDate.getDay().getDayId())
                        .map(day -> day.getDay())
                        .orElse("");
                dateResponseDto.setDay(dayName);

                // ================= COMMENT =================

                dateResponseDto.setComment(firstHoliday.getComment());

                // ================= CREATED BY =================

                dateResponseDto.setCreatedBy(firstHoliday.getCreatedBy());

                // ================= HOLIDAY DTO =================

                List<ResponseHolidayDto> holidayDtos = holidays.stream()
                        .map(h -> {
                            ResponseHolidayDto dto = new ResponseHolidayDto();
                            dto.setHolidayId(h.getHolidayId());
                            dto.setHolidayName(h.getHolidayName());

                            // ✅ status lookup
                           Long statusId = holidayIdToStatusId.get(h.getHolidayId());
                            if (statusId != null) {
                                Approval status = statusMap.get(statusId);
                                if (status != null) {
                                    dto.setStatusId(status.getApproval_Status_Id());
                                    dto.setStatusCode(status.getStatus());
                                    dto.setStatusDescription(status.getDescription());
                                }
                            }

                            return dto;
                        }).toList();

                dateResponseDto.setHolidays(holidayDtos);
                dateResponseDtos.add(dateResponseDto);
            }

            // ================= SORT BY DATE =================

            dateResponseDtos.sort(Comparator.comparing(DateResponseDto::getDate));

            // ================= COUNTRY DTO =================

            Country country = countryRepository.findById(countryId)
                    .orElseThrow(() -> new RuntimeException("Country not found"));

            CountryResponsDto countryDto = new CountryResponsDto();
            countryDto.setCountryId(country.getCountryId());
            countryDto.setCountryName(country.getCountryName());

            // ================= CITY DTO =================

            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new RuntimeException("City not found"));

            CityDto cityDto = new CityDto();
            cityDto.setCityId(city.getCityId());
            cityDto.setCityName(city.getCityName());

            // ================= YEAR DTO =================

            Year year = yearRepository.findById(yearId)
                    .orElseThrow(() -> new RuntimeException("Year not found"));

            YearResponseDto yearDto = new YearResponseDto();
            yearDto.setYearId(year.getYearId());
            yearDto.setYear(year.getYear());

            // ================= FINAL RESPONSE =================

            projectHolidayResponseDto response = new projectHolidayResponseDto();
            response.setCountry(countryDto);
            response.setCityId(cityDto);
            response.setYear(yearDto);
            response.setDates(dateResponseDtos);

            return List.of(response);
        }


    @Override
    public void saveholiday(ProjectHolidayRequestDto request) {
        try {
            Long countryId = request.getCountryId();
            Long yearId = request.getYearId();

            // ✅ fetch cityId and industryId from project table
            ProjectDetails project = projectDetailsRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            Long projectId = project.getProjectDetailsId();
            Long cityId = project.getProjectCityId();
            Long industryId = project.getIndustryId();

            for (RequestDateDto dateReq : request.getDates()) {
                Long dateId = dateReq.getDateId();

                date date = dateRepository.findById(dateId)
                        .orElseThrow(() -> new RuntimeException("Date not found"));
                String setday = date.getDay().getDay();

                for (RequestHoldiayDTO holidayDto : dateReq.getHolidays()) {
                    String originalName = holidayDto.getHolidayName().trim();
                    String normalizedName = originalName.replaceAll("\\s+", " ").toLowerCase();

                    // ── 1. Global duplicate check ────────────────────────────
                    boolean globalExists = holidayRepository
                            .existsByCountryIdAndYearIdAndDateIdAndHolidayNameIgnoreCase(
                                    countryId, yearId, dateId, normalizedName);
                    if (globalExists) {
                        throw new RuntimeException(
                                "Holiday already exists in Global Holiday for this date: " + normalizedName);
                    }

                    // ── 2. Save to global Holiday table ──────────────────────
                    Holiday h = new Holiday();
                    h.setCountryId(countryId);
                    h.setYearId(yearId);
                    h.setDateId(dateId);
                    h.setDay(setday);
                    h.setHolidayName(originalName);
                    h.setComment(dateReq.getComment());
                    h.setCreatedBy(dateReq.getCreatedBy());
                    h.setCreateDate(LocalDateTime.now());

                    Holiday savedHoliday = holidayRepository.save(h);

                    // ── 3. Project duplicate check ───────────────────────────
                    boolean projectExists = projectHolidayRepository
                            .existsByProjectIdAndHolidayId(projectId, savedHoliday.getHolidayId());
                    if (projectExists) {
                        throw new RuntimeException(
                                "Holiday already linked to this project: " + normalizedName);
                    }
                    // ✅ fetch Saved status by code
                    Approval savedStatus = statusMasterRepositories
                            .findByStatus("Saved")
                            .orElseThrow(() -> new RuntimeException("Status 'Saved' not found"));

                    // ── 4. Save to ProjectHoliday table ──────────────────────
                    ProjectHoliday ph = new ProjectHoliday();
                    ph.setProjectId(projectId);
                    ph.setIndustryId(industryId);   // ✅ from project table
                    ph.setCityId(cityId);           // ✅ from project table
                    ph.setHolidayId(savedHoliday.getHolidayId());
                    ph.setCreatedBy(dateReq.getCreatedBy());
                    ph.setCreateDate(LocalDateTime.now());
                    ph.setStatusId(savedStatus.getApproval_Status_Id());

                    projectHolidayRepository.save(ph);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());


        }
    }

    @Override
    public void updateProjectHoliday(HolidayRequestDto request) {

        try {
            Long countryId = request.getCountryId();
            Long yearId    = request.getYearId();

            for (RequestDateDto dateReq : request.getDates()) {
                for (RequestHoldiayDTO holidayDto : dateReq.getHolidays()) {

                    // ── 1. Find existing global Holiday ──────────────────────
                    Holiday existing = holidayRepository.findById(holidayDto.getHolidayId())
                            .orElseThrow(() -> new RuntimeException(
                                    "Holiday not found with id: " + holidayDto.getHolidayId()));

                    String originalName   = holidayDto.getHolidayName().trim();
                    String normalizedName = originalName.replaceAll("\\s+", "").toLowerCase();

                    Long targetDateId = dateReq.getDateId() != null
                            ? dateReq.getDateId()
                            : existing.getDateId();

                    // ── 2. Duplicate check ───────────────────────────────────
                    boolean exists = holidayRepository.existsDuplicateOnUpdate(
                            countryId, yearId, targetDateId, normalizedName, holidayDto.getHolidayId());
                    if (exists) {
                        throw new RuntimeException(
                                "Holiday already exists for this date: " + originalName);
                    }

                    // ── 3. Update global Holiday ─────────────────────────────
                    existing.setHolidayName(holidayDto.getHolidayName());
                    existing.setComment(dateReq.getComment());

                    // update comment on all holidays of same date
                    List<Holiday> sameDateHolidays = holidayRepository
                            .findByCountryIdAndYearIdAndDateId(countryId, yearId, targetDateId);
                    for (Holiday h : sameDateHolidays) {
                        h.setComment(dateReq.getComment());
                    }
                    holidayRepository.saveAll(sameDateHolidays);

                    if (dateReq.getDateId() != null) {
                        existing.setDateId(dateReq.getDateId());
                        date newDate = dateRepository.findById(dateReq.getDateId())
                                .orElseThrow(() -> new RuntimeException("Date not found"));
                        existing.setDay(newDate.getDay().getDay());
                    }
                    holidayRepository.save(existing);

                    // ── 4. Update ProjectHoliday table ───────────────────────
                    ProjectHoliday ph = projectHolidayRepository
                            .findByHolidayId(existing.getHolidayId())
                            .orElseThrow(() -> new RuntimeException(
                                    "ProjectHoliday not found for holidayId: " + existing.getHolidayId()));


                    ph.setUpdateDate(LocalDateTime.now());

                    projectHolidayRepository.save(ph);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}






