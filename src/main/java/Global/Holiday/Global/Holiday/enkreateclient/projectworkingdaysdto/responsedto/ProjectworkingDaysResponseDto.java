package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto;

import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.RequestDatesDto;
import lombok.Data;

import java.util.List;


@Data
public class ProjectworkingDaysResponseDto {


    private Long projectId;
    private String projectName;
    private List<ResponseDatesDto> dates;

}
