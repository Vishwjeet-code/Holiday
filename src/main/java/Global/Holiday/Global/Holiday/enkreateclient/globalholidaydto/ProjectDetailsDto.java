package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDetailsDto {

    @Id
    private Long projectDetailsId;

    private String projectName;

    private Long projectCityId;

    private Long projectCityPinId;

}
