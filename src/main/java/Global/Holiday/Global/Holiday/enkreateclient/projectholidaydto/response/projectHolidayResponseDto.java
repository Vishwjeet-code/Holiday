package Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.response;

import Global.Holiday.Global.Holiday.admin.dtos.CityDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.CountryResponsDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.DateResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.YearResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class projectHolidayResponseDto {

    private CountryResponsDto country;
    private CityDto cityId;
    private YearResponseDto year;
    private List<DateResponseDto> dates;
}
