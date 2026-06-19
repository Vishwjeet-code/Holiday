package Global.Holiday.Global.Holiday.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateDto {
    private Long stateId;
    private String stateCode;
    private String stateName;
    private String stateType;

    private Long countryId;

    private String createdBy;
    private LocalDateTime createDateTime;
    private String updatedBy;
    private LocalDateTime updateDateTime;
}
