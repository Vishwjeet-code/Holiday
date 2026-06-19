package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayResponseDto {

    private CountryResponsDto country;
    private YearResponseDto year;
    private List<DateResponseDto> dates;
}
