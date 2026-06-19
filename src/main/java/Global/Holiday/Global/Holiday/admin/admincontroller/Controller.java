package Global.Holiday.Global.Holiday.admin.admincontroller;


import Global.Holiday.Global.Holiday.admin.dtos.*;

import Global.Holiday.Global.Holiday.admin.services.*;

import Global.Holiday.Global.Holiday.enkreateclient.services.ProjectDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@Tag(name = "Enkreate APIs",description = "Get Countries, Get Years, Get Date By YearID")
@RequestMapping("/api")
public class Controller {
    private final CityService cityService;
    private final CountryService countryService;
    private final StateService stateService;
    private final ContinentService continentService;
    private final IndustryService industryService;
    private final YearService yearService;
    private final MonthService monthService;
    private final WeekService weekService;
    private final DayService dayService;
    private final QuarterService quarterService;
    private final DateService dateMasterService;
    private final ProjectDetailService projectDetailService;

    public Controller(
            CityService cityService,
            CountryService countryService,
            StateService stateService,
            ContinentService continentService,
            IndustryService industryService,
            YearService yearService,
            MonthService monthService,
            WeekService weekService,
            DayService dayService,
            QuarterService quarterService,
            DateService dateMasterService, ProjectDetailService projectDetailService
    ) {
        this.cityService = cityService;
        this.countryService = countryService;
        this.stateService = stateService;
        this.continentService = continentService;
        this.industryService = industryService;
        this.yearService = yearService;
        this.monthService = monthService;
        this.weekService = weekService;
        this.dayService = dayService;
        this.quarterService = quarterService;
        this.dateMasterService = dateMasterService;
        this.projectDetailService = projectDetailService;
    }

    @Operation(summary = "Fetching all country ")
    @GetMapping("/countries/all")
    public List<CountryDto> getCountries() {
        return countryService.getAllCountries();
    }

    @Operation(summary = "Fetching all years")
    @GetMapping("/years/all")
    public List<YearDto> getYears() {
        return yearService.getAll();
    }
//    @GetMapping("/date/all")
//    public List<DateDTO> getAll(){
//        return dateMasterService.getAll();
//    }

    @Operation(summary = "fetching date by yearId")
    @GetMapping("/date/{yearId}")
    public List<DateDto> getByYear(@PathVariable Long yearId) {
        return dateMasterService.getByYear(yearId);
    }



    @Operation(summary = "fetching cities by cityId")
    @GetMapping("/cities/{cityId}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long cityId) {
        CityDto city = cityService.getById(cityId);
        return ResponseEntity.ok(city);
    }
    @GetMapping("/AllDays")
    public List<DayDto> getAllDays(){
        return dayService.getAll();
    }
}
