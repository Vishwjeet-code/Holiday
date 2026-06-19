package Global.Holiday.Global.Holiday.enkreateclient.controller;



import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectTask;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.ProjectDetailsDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.CopyGlobalHolidayToProjectHolidayDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.ProjectHolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.response.projectHolidayResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos.CopyRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.HolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.HolidayResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.ProjectworkingDaysRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto.ProjectworkingDaysResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto.ProjectWorkingDaysUpdateDto;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.ProjectTaskRepository;
import Global.Holiday.Global.Holiday.enkreateclient.services.*;
import Global.Holiday.Global.Holiday.enkreateclient.serviceImpls.holidayServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mpxj.ProjectFile;
import org.mpxj.Task;
import org.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Client APIs" ,description = "Get Holdiday by CountryID and YearID , Save Holiday")
@RequestMapping("/api")
public class HolidayController {


    private final ProjectMmpService projectMmpService;
    //    private final holidayServiceImpl holidayService;
    private final holidayService holidayService;
    private final ProjectHolidayService projectHolidayService;
    private final ProjectDetailService projectDetailService;
    private final ProjectWorkingDaysService projectWorkingDaysService;

    public HolidayController(ProjectMmpService projectMmpService, holidayServiceImpl holidayService, ProjectHolidayService projectHolidayService, ProjectDetailService projectDetailService, ProjectWorkingDaysService projectWorkingDaysService) {
        this.projectMmpService = projectMmpService;
        this.holidayService = holidayService;
        this.projectHolidayService = projectHolidayService;
        this.projectDetailService = projectDetailService;
        this.projectWorkingDaysService = projectWorkingDaysService;
    }

    @Operation(summary = "Saving Global holiday on single country and year ")
    @PostMapping("/save")
    public void saveHoliday(@RequestBody HolidayRequestDto holidayRequestDto) {

        holidayService.saveholiday(holidayRequestDto);
    }

    //    @GetMapping("/all")
//    public List<HolidayResponse> getAll() {
//        return holidayService.getAllHolidays();
//
//    }
    @Operation(summary = "Getting Global Holiday by countryId and YearId")
    @GetMapping("/holidays/{countryId}/{yearId}")
    public List<HolidayResponseDto> getbyCountryIDandYearID(@PathVariable Long countryId,
                                                            @PathVariable Long yearId) {

        return holidayService.getByCountryAndYear(countryId, yearId);

    }

    @Operation(summary = "updating each Global holiday")
    @PutMapping("/update")
    public ResponseEntity<?> updateHoliday(@RequestBody HolidayRequestDto request) {
        try {
            holidayService.updateHoliday(request);
            return ResponseEntity.ok("Updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Operation(summary = "copying holidays from previous year")
    @PostMapping("/copy-selected")
    public ResponseEntity<String> copySelected(@RequestBody CopyRequestDto request) {

        holidayService.copySelectedHolidays(request);

        return ResponseEntity.ok("Copied successfully");
    }

    @Operation(summary = "Copy Project Holiday from Global Holiday on single country, year and city ")
    @PostMapping("/copyProjectHoliday")
    public void saveProjectHoliday(@RequestBody CopyGlobalHolidayToProjectHolidayDto projectHolidayRequestDto) {

        projectHolidayService.saveProjectHoliday(projectHolidayRequestDto);
    }

//    @PutMapping("/update/projectHoliday")
//    public ResponseEntity<String> updateProjectHoliday(@RequestBody ProjectHolidayUpdateDto request) {
//        projectHolidayService.updateProjectHoliday(request);
//        return ResponseEntity.ok("Project holiday updated successfully");
//
//    }

    @Operation(summary = "Getting Project Holiday by projectId, CountryId,yearId")
    @GetMapping("/projectHoliday/{projectId}/{countryId}/{yearId}")
    public List<projectHolidayResponseDto> getProjectHolidays(
            @PathVariable Long projectId,
            @PathVariable Long countryId,
            @PathVariable Long yearId) {

        return projectHolidayService.getProjectHolidays(
                projectId,
                countryId,
                yearId
        );
    }

    @Operation(summary = "Fetching all projects")
    @GetMapping("/projects/all")
    public List<ProjectDetailsDto> getProjects() {
        return projectDetailService.getAllProjects();
    }

    @Operation(summary = "Saving Project holiday on single country , year and project based on city ")
    @PostMapping("/projectHolidaySave")
    public ResponseEntity<?> saveProjectHoliday(@RequestBody ProjectHolidayRequestDto holidayRequestDto) {

        try {
            projectHolidayService.saveholiday(holidayRequestDto);
            return ResponseEntity.ok("Saved successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // ✅ 400 with message
        }
    }

    @PutMapping("/update/projectHoliday")
    public void updateProjectHoliday(@RequestBody HolidayRequestDto request) {
        projectHolidayService.updateProjectHoliday(request);
    }

    @PostMapping("/SavePojectWorkingDays")
    public void saveProjectWorkingDays(@RequestBody ProjectworkingDaysRequestDto projectworkingDaysRequestDto) {
        projectWorkingDaysService.saveProjectWorkingDasy(projectworkingDaysRequestDto);
    }

    @GetMapping("/AllProjectWorkingDay/{projectId}")
    public List<ProjectworkingDaysResponseDto> getAllProjectWorkingDays(@PathVariable Long projectId) {
        return projectWorkingDaysService.getAll(projectId);
    }

    @PutMapping("/update/ProjectWorkingDays")
    public void updateProjectWorkingDays(@RequestBody ProjectWorkingDaysUpdateDto projectWorkingDaysUpdateDtoDto) {

        projectWorkingDaysService.updateProjectWorkingDays(projectWorkingDaysUpdateDtoDto);
    }

    @PostMapping("/import")
    public ResponseEntity<?> importMpp(@RequestParam("file") MultipartFile file) {
        projectMmpService.save(file);
        return ResponseEntity.ok("MPP file imported successfully");
    }
    @GetMapping("/tasks")
    public List<ProjectTask> getAllTasks() {
       return projectMmpService.findAll();
    }

}


