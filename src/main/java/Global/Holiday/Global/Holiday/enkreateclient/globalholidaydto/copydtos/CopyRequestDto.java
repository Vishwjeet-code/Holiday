package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyRequestDto {
    private Long fromYear;
    private Long toYear;
    private Long countryId;
    private List<HolidayCopyDto> holidays;
}
