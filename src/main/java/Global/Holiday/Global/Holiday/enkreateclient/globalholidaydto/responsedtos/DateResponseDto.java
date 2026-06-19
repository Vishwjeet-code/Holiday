package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DateResponseDto {

    private Long dateId;
    private LocalDate date;
    private String day;
    private String comment;
    private String createdBy;
    private List<ResponseHolidayDto> holidays; // 👈 ADD THIS

}
