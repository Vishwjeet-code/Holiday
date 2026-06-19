package Global.Holiday.Global.Holiday.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContinentDto {
    private Long continentId;
    private String continentCode;
    private String continentName;
    private String createdBy;
    private LocalDateTime createDateTime;
    private String updatedBy;
    private LocalDateTime updateDateTime;

}
