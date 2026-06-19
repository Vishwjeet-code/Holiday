package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class RequestDatesDto {

    private Long dayId;

    private char workingFlag;
    private LocalTime startTime;
    private LocalTime endTime;
    private String createdBy;

}
