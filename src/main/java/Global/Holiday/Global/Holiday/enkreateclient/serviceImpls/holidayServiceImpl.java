package Global.Holiday.Global.Holiday.enkreateclient.serviceImpls;


import Global.Holiday.Global.Holiday.admin.entities.*;
import Global.Holiday.Global.Holiday.admin.repositories.*;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.*;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos.CopyHolidayItem;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos.HolidayCopyDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos.CopyRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.HolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.RequestDateDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.RequestHoldiayDTO;
import Global.Holiday.Global.Holiday.enkreateclient.entities.Holiday;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.HolidayRepository;
import Global.Holiday.Global.Holiday.enkreateclient.services.holidayService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class holidayServiceImpl implements holidayService {

    private final HolidayRepository holidayRepository;
    private final CountryRepository countryRepository;
    private final YearRepository yearRepository;
    private final DateRepository dateRepository;
    private final DayRepository dayRepository;
    private final QuarterRepository quarterRepository;
    private final MonthRepository monthRepository;
    private final WeekRepository weekRepository;

    public holidayServiceImpl(HolidayRepository holidayRepository, CountryRepository countryRepository, YearRepository yearRepository, DateRepository dateRepository, DayRepository dayRepository, QuarterRepository quarterRepository, MonthRepository monthRepository, WeekRepository weekRepository) {
        this.holidayRepository = holidayRepository;
        this.countryRepository = countryRepository;
        this.yearRepository = yearRepository;
        this.dateRepository = dateRepository;
        this.dayRepository = dayRepository;
        this.quarterRepository = quarterRepository;
        this.monthRepository = monthRepository;
        this.weekRepository = weekRepository;
    }


    @Override
    public void saveholiday(HolidayRequestDto request) {
        try {
            // we will fetch countryId from request
            Long countryId = request.getCountryId();
            // we will fetch yearId from request
            Long yearId = request.getYearId();


            // this loops work for RequestdateDto List for the fetching day
            for (RequestDateDto dateReq : request.getDates()) {
                Long dateId = dateReq.getDateId();
                date date;
                date = dateRepository.findById(dateReq.getDateId()).orElseThrow(()
                        -> new RuntimeException("Date not found"));
                String setday = date.getDay().getDay();

                // this loop work for RequestHolidayDto list for the checking duplication of holiday and setting holiday in db
                for (RequestHoldiayDTO holidayDto : dateReq.getHolidays()) {
                    String originalName = holidayDto.getHolidayName().trim(); // fetched holiday Name
                    String name = originalName.replaceAll("\\s+", " ").toLowerCase(); // ✅ collapse inner spaces too // for the checking duplication

                    boolean exists = holidayRepository // checking holiday name in database if duplicate name get loop get exit
                            .existsByCountryIdAndYearIdAndDateIdAndHolidayNameIgnoreCase(
                                    countryId,
                                    yearId,
                                    dateId,
                                    name);
                    if (exists) {
                        throw new RuntimeException("Holiday already exists for this date: " + name);
                    }

                    // setting holiday in db
                    Holiday h = new Holiday();
                    h.setCountryId(countryId);
                    h.setYearId(yearId);
                    h.setDateId(dateId);
                    h.setDay(setday);
                    h.setHolidayName(originalName);
                    h.setComment(dateReq.getComment());
                    h.setCreatedBy(dateReq.getCreatedBy());
                    h.setCreateDate(LocalDateTime.now());
                    holidayRepository.save(h);
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<HolidayResponseDto> getAllHolidays() {

        List<Holiday> holidayList = holidayRepository.findAll();

        // ✅ Step 1: Collect all IDs
        Set<Long> countryIds = new HashSet<>();
        Set<Long> yearIds = new HashSet<>();
        Set<Long> dateIds = new HashSet<>();

        for (Holiday h : holidayList) {
            countryIds.add(h.getCountryId());
            yearIds.add(h.getYearId());
            dateIds.add(h.getDateId());
        }

        // ✅ Step 2: Bulk fetch (VERY IMPORTANT)
        Map<Long, Country> countryMap = countryRepository.findAllById(countryIds)
                .stream().collect(Collectors.toMap(Country::getCountryId, c -> c));

        Map<Long, Year> yearMap = yearRepository.findAllById(yearIds)
                .stream().collect(Collectors.toMap(Year::getYearId, y -> y));

        Map<Long, date> dateMap = dateRepository.findAllById(dateIds)
                .stream().collect(Collectors.toMap(date::getDateId, d -> d));

        // ✅ Step 3: Main response map
        Map<String, HolidayResponseDto> responseMap = new LinkedHashMap<>();

        for (Holiday h : holidayList) {

            Country country = countryMap.get(h.getCountryId());
            Year year = yearMap.get(h.getYearId());
            date dateEntity = dateMap.get(h.getDateId());

            // ⚠️ Skip if any data missing
            if (country == null || year == null || dateEntity == null) {
                continue;
            }

            String key = h.getCountryId() + "-" + h.getYearId();

            // ✅ Create or get response
            HolidayResponseDto response = responseMap.computeIfAbsent(key, k -> {
                HolidayResponseDto res = new HolidayResponseDto();

                res.setCountry(new CountryResponsDto(
                        country.getCountryId(),
                        country.getCountryName()
                ));

                res.setYear(new YearResponseDto(
                        year.getYearId(),
                        year.getYear()
                ));

                res.setDates(new ArrayList<>());
                return res;
            });

            // ✅ Find or create DateDTO
            DateResponseDto dateResponseDTO = response.getDates().stream()
                    .filter(d -> d.getDateId().equals(h.getDateId()))
                    .findFirst()
                    .orElse(null);

            if (dateResponseDTO == null) {
                dateResponseDTO = new DateResponseDto();
                dateResponseDTO.setDateId(dateEntity.getDateId());
                dateResponseDTO.setDate(dateEntity.getDate());
                dateResponseDTO.setDay(dateEntity.getDay().getDay());
                dateResponseDTO.setCreatedBy(h.getCreatedBy());
                dateResponseDTO.setHolidays(new ArrayList<>());


                response.getDates().add(dateResponseDTO);
            }


            ResponseHolidayDto responseHolidayDTO = new ResponseHolidayDto();
            responseHolidayDTO.setHolidayId(h.getHolidayId());
            responseHolidayDTO.setHolidayName(h.getHolidayName());
//


            dateResponseDTO.getHolidays().add(responseHolidayDTO);
        }

        return new ArrayList<>(responseMap.values());
    } // it is not in use right now


    @Override
    public List<HolidayResponseDto> getByCountryAndYear(Long countryId, Long yearId) {

        try {

            List<Holiday> holidayList = // fetching all holidays from db
                    holidayRepository.findByCountryIdAndYearId(countryId, yearId);

            // if no holiday fount then return no need to go further
            if (holidayList.isEmpty()) {
                return new ArrayList<>();
            }

            // fetching date id from holiday List in set for saving unique dateid
            Set<Long> dateIds = holidayList.stream()
                    .map(Holiday::getDateId)
                    .collect(Collectors.toSet());

            // mapping dateId with dates by calling date repository
            Map<Long, date> dateMap = dateRepository.findAllById(dateIds)
                    .stream().collect(Collectors.toMap(date::getDateId, d -> d));

            // fetching country by country repository
            Country country = countryRepository.findById(countryId).orElse(null);

            // fetching year by year repository
            Year year = yearRepository.findById(yearId).orElse(null);

            // if country and year is not null then go ahead otherwise return Empty arrayList
            if (country == null || year == null) {
                return new ArrayList<>();
            }

            // creating response structure by responseDto
            HolidayResponseDto response = new HolidayResponseDto();

            // setting country object in response structure
            response.setCountry(new CountryResponsDto(
                    country.getCountryId(),
                    country.getCountryName()
            ));

            // setting year object in response structure
            response.setYear(new YearResponseDto(
                    year.getYearId(),
                    year.getYear()
            ));

            // creating Empty arrayList in structure
            response.setDates(new ArrayList<>());

            // Empty HashMap is for checking for date added or not
            Map<Long, DateResponseDto> dateResponseMap = new LinkedHashMap<>();

            //loop for holiday from holidaylist
            for (Holiday h : holidayList) {
                date dateEntity = dateMap.get(h.getDateId()); // fetching date from dateMap
                if (dateEntity == null) continue;

                // Check if DateResponseDto already exists for this date
                //if exist then it will add old dateResponse with new holiday on the same dateId
                DateResponseDto dateResponseDTO = dateResponseMap.get(h.getDateId());

                // If not exists, create new DateResponseDto
                if (dateResponseDTO == null) {
                    // creation of new dateResponse
                    dateResponseDTO = new DateResponseDto();

                    // Set date details
                    dateResponseDTO.setDateId(dateEntity.getDateId());
                    dateResponseDTO.setDate(dateEntity.getDate());
                    dateResponseDTO.setDay(dateEntity.getDay().getDay());
                    dateResponseDTO.setHolidays(new ArrayList<>());
                    dateResponseDTO.setComment(h.getComment());
                    dateResponseDTO.setCreatedBy(h.getCreatedBy());
                    dateResponseMap.put(h.getDateId(), dateResponseDTO); // ✅
                    response.getDates().add(dateResponseDTO);
                }

                // creating ResponseHolidayDto
                ResponseHolidayDto responseHolidayDTO = new ResponseHolidayDto();
                // setting holidayId
                responseHolidayDTO.setHolidayId(h.getHolidayId());
                // setting holidayName
                responseHolidayDTO.setHolidayName(h.getHolidayName());

                dateResponseDTO.getHolidays().add(responseHolidayDTO);
            }
            // returning response
            return List.of(response);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void updateHoliday(HolidayRequestDto request) {
        try {
            // fetching countryId from the request
            Long countryId = request.getCountryId();
            // fetching yearId from the request
            Long yearId = request.getYearId();

            // loop for RequestDateDto for request
            for (RequestDateDto dateReq : request.getDates()) {

                // loop for RequestHoliday dto for the request
                for (RequestHoldiayDTO holidayDto : dateReq.getHolidays()) {

                    // 🔥 Find by holidayId directly
                    Holiday existing = holidayRepository.findById(holidayDto.getHolidayId())
                            .orElseThrow(() -> new RuntimeException("Holiday not found with id: " + holidayDto.getHolidayId()));

                    String originalName = holidayDto.getHolidayName().trim();
                    String normalizedName = originalName.replaceAll("\\s+", "").toLowerCase();

                    Long targetDateId = dateReq.getDateId() != null ? dateReq.getDateId() : existing.getDateId();

                    boolean exists = holidayRepository.existsDuplicateOnUpdate(
                            countryId, yearId, targetDateId, normalizedName, holidayDto.getHolidayId());

                    if (exists) {
                        throw new RuntimeException("Holiday already exists for this date: " + originalName);
                    }

                    // setting or updating holidayName and comment
                    existing.setHolidayName(holidayDto.getHolidayName());
                    existing.setComment(dateReq.getComment());

                    List<Holiday> sameDateHolidays =
                            holidayRepository.findByCountryIdAndYearIdAndDateId(
                                    countryId,
                                    yearId,
                                    targetDateId
                            );

                    for (Holiday h : sameDateHolidays) {
                        h.setComment(dateReq.getComment());
                    }

                    holidayRepository.saveAll(sameDateHolidays);
                    // checking if date is not null
                    if (dateReq.getDateId() != null) {
                        // if not set dateId
                        existing.setDateId(dateReq.getDateId());

                        // create date object and find the  data object
                        date newDate = dateRepository.findById(dateReq.getDateId())
                                .orElseThrow(() -> new RuntimeException("Date not found"));
                        // setting or updating day from the date object
                        existing.setDay(newDate.getDay().getDay());
                    }

                    // saved date
                    holidayRepository.save(existing);

                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void copySelectedHolidays(CopyRequestDto request) {

        try {
            // fetching previous yearId from the request
            Long sourceYearId = request.getFromYear();
            // fetching target year id from the request
            Long targetYearId = request.getToYear();
            // fetching countryId from the request
            Long countryId = request.getCountryId();

            // find previous year from the repository
            Year sourceYear = yearRepository.findById(sourceYearId)
                    .orElseThrow(() -> new RuntimeException("Source year not found"));

            // finding target year from the repository
            Year targetYear = yearRepository.findById(targetYearId)
                    .orElseThrow(() -> new RuntimeException("Target year not found"));

            // this loop for copy the holiday
            for (HolidayCopyDto dto : request.getHolidays()) {

                // fetching previous date from the repository
                date sourceDate = dateRepository.findById(dto.getDateId())
                        .orElseThrow(() -> new RuntimeException("Source date not found"));

                // 🔥 IMPORTANT: use targetYear ONLY
                LocalDate targetDateValue = sourceDate.getDate()
                        .withYear(Integer.parseInt(targetYear.getYear()));

                // fetching dates from selected year from the repository
                date targetDate = dateRepository
                        .findByDateAndYear_YearId(targetDateValue, targetYear.getYearId())
                        .orElseThrow(() -> new RuntimeException(
                                "Date not found in target year: " + targetDateValue));

                // this loop for copying holidays item
                for (CopyHolidayItem h : dto.getHolidays()) {

                    // this boolean checking is there any same previous year holiday present in targeted year
                    boolean exists = holidayRepository
                            .existsByCountryIdAndYearIdAndDateIdAndHolidayNameIgnoreCase(
                                    countryId,
                                    targetYear.getYearId(),
                                    targetDate.getDateId(),
                                    h.getHolidayName()
                            );
                    // if true skin below code if false go further in code
                    if (exists) continue;

                    // creating holiday object
                    Holiday newHoliday = new Holiday();
                    //setting details
                    newHoliday.setCountryId(countryId);
                    newHoliday.setYearId(targetYear.getYearId());
                    newHoliday.setDateId(targetDate.getDateId());
                    newHoliday.setHolidayName(h.getHolidayName());
                    newHoliday.setComment(dto.getComment());
                    newHoliday.setCreateDate(LocalDateTime.now());
                    newHoliday.setDay(targetDate.getDay().getDay());

                    // saving holiday in target year
                    holidayRepository.save(newHoliday);
                }
            }
        }catch (RuntimeException e){
            throw  e;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}



