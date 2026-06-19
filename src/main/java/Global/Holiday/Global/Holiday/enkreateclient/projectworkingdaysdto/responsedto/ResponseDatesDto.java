package Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ResponseDatesDto {

    private Long dayId;
    private String day;
    private char workingFlag;
    private LocalTime startTime;
    private LocalTime endTime;
    private String createdBy;

}
