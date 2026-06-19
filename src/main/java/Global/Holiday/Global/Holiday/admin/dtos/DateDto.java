package Global.Holiday.Global.Holiday.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class DateDto {
    private Long dateId;
    private LocalDate date;

    private Long dayId;
    private Long weekId;
    private Long monthId;
    private Long quarterId;
    private Long yearId;

    private String createdBy;
    private LocalDateTime createDateTime;
    private String updatedBy;
    private LocalDateTime updateDateTime;
}
