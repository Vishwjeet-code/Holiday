package Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto;

import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.RequestDateDto;
import lombok.Data;

import java.util.List;

@Data
public class ProjectHolidayRequestDto {

    private Long projectId;
    private Long countryId;
    private Long yearId;
    private List<RequestDateDto> dates;
}
