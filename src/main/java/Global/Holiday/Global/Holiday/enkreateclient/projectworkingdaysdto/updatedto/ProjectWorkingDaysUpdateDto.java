package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto;

import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.RequestDatesDto;
import lombok.Data;

import java.util.List;

@Data
public class ProjectWorkingDaysUpdateDto {

    private Long projectId;
    private List<UpdateRequestDatesDto> dates;
}
