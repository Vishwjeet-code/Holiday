package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayCopyDto {
    private String date;
    private Long dateId;
    private String comment;
    private List<CopyHolidayItem> holidays;
}
