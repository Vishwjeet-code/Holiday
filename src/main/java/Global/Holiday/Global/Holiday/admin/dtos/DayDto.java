package Global.Holiday.Global.Holiday.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayDto {
    private Long dayId;
    private String day;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String updatedBy;
    private LocalDateTime updateDateTime;

}
