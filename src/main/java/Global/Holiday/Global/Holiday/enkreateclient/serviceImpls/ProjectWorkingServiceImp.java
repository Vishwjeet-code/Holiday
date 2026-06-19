package Global.Holiday.Global.Holiday.enkreateclient.serviceImpls;

import Global.Holiday.Global.Holiday.admin.dtos.DayDto;
import Global.Holiday.Global.Holiday.admin.entities.Day;
import Global.Holiday.Global.Holiday.admin.entities.date;
import Global.Holiday.Global.Holiday.admin.repositories.*;
import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectWorkingDays;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.ProjectworkingDaysRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.RequestDatesDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto.ProjectworkingDaysResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto.ResponseDatesDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto.ProjectWorkingDaysUpdateDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto.UpdateRequestDatesDto;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.*;
import Global.Holiday.Global.Holiday.enkreateclient.services.ProjectWorkingDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectWorkingServiceImp implements ProjectWorkingDaysService {

    @Autowired
    private final ProjectWorkingDaysRepository projectWorkingDaysRepository;
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

    public ProjectWorkingServiceImp(ProjectWorkingDaysRepository projectWorkingDaysRepository, ProjectDetailsRepository projectDetailsRepository, HolidayRepository holidayRepository, ProjectHolidayRepository projectHolidayRepository, CountryRepository countryRepository, YearRepository yearRepository, DateRepository dateRepository, DayRepository dayRepository, QuarterRepository quarterRepository, MonthRepository monthRepository, WeekRepository weekRepository, CityRepository cityRepository, IndustryRepository industryRepository, ApprovalRepositories statusMasterRepositories) {
        this.projectWorkingDaysRepository = projectWorkingDaysRepository;
        this.projectDetailsRepository = projectDetailsRepository;
        this.holidayRepository = holidayRepository;
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
        this.statusMasterRepositories = statusMasterRepositories;
    }


    @Override
    public void saveProjectWorkingDasy(ProjectworkingDaysRequestDto projectworkingDaysRequestDto) {

        try {
            Long projectId = projectworkingDaysRequestDto.getProjectId();

            for (RequestDatesDto requestDates : projectworkingDaysRequestDto.getDates()) {
                ProjectWorkingDays entity = new ProjectWorkingDays();

                entity.setProjectId(projectId);



                entity.setDayId(requestDates.getDayId());

                entity.setWorkingFlag(requestDates.getWorkingFlag());

                entity.setWorkStartTime(requestDates.getStartTime());

                entity.setWorkEndTime(requestDates.getEndTime());

                entity.setCreatedBy(requestDates.getCreatedBy());

                entity.setCreateDateTime(LocalDateTime.now());

                // save entity
                projectWorkingDaysRepository.save(entity);
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<ProjectworkingDaysResponseDto> getAll(Long projectId) {

        List<ProjectWorkingDays> projectWorkingDays =
                projectWorkingDaysRepository.findByProjectId(projectId);

        if (projectWorkingDays.isEmpty()) {
            return new ArrayList<>();
        }

        ProjectworkingDaysResponseDto response =
                new ProjectworkingDaysResponseDto();

        response.setProjectId(projectId);

        String projectName = projectDetailsRepository
                .findById(projectId)
                .map(p -> p.getProjectName())
                .orElse("");

        response.setProjectName(projectName);

        List<ResponseDatesDto> dates = new ArrayList<>();

        for (ProjectWorkingDays entity : projectWorkingDays) {

            ResponseDatesDto dateDto = new ResponseDatesDto();

            dateDto.setDayId(entity.getDayId());

            Day day = dayRepository.findById(entity.getDayId())
                    .orElseThrow(() -> new RuntimeException("Day not found"));

            dateDto.setDay(day.getDay());

            dateDto.setWorkingFlag(entity.getWorkingFlag());

            dateDto.setStartTime(entity.getWorkStartTime());

            dateDto.setEndTime(entity.getWorkEndTime());

            dateDto.setCreatedBy(entity.getCreatedBy());

            dates.add(dateDto);
        }

        response.setDates(dates);

        return List.of(response);
    }

    @Override
    public void updateProjectWorkingDays(ProjectWorkingDaysUpdateDto projectWorkingDaysUpdateDto) {
        try {



            for (UpdateRequestDatesDto request : projectWorkingDaysUpdateDto.getDates()) {

                if (request.getDayId() == null) {
                    throw new RuntimeException("DayId is required");
                }

                ProjectWorkingDays entity =
                        projectWorkingDaysRepository
                                .findByProjectIdAndDayId(
                                        projectWorkingDaysUpdateDto.getProjectId(),
                                        request.getDayId()
                                )
                                .orElseThrow(() ->
                                        new RuntimeException("Working day not found")
                                );

                entity.setWorkingFlag(request.getWorkingFlag());

                entity.setWorkStartTime(request.getStartTime());

                entity.setWorkEndTime(request.getEndTime());

                entity.setUpdatedBy(request.getCreated());

                projectWorkingDaysRepository.save(entity);
            }

        } catch (RuntimeException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error while updating working days"
            );
        }
    }
}
