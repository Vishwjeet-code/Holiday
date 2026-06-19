package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseHolidayDto {
    private Long holidayId;
    private String holidayName;
    private Long statusId;
    private String statusCode;
    private String statusDescription;


}
