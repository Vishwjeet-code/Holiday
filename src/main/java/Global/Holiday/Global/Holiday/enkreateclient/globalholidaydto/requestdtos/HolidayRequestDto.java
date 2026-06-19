package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayRequestDto {

    private Long countryId;
    private Long yearId;
    private List<RequestDateDto> dates;
}
