package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectworkingDaysRequestDto {

    private Long projectId;
    private List<RequestDatesDto> dates;
}
