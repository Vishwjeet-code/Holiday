package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class UpdateRequestDatesDto {

    private Long dayId;

    private char workingFlag;
    private LocalTime startTime;
    private LocalTime endTime;
    private String created;

}
