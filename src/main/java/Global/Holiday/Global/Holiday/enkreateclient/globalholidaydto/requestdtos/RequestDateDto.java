package Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDateDto {

    private Long dateId;
    private String comment;
    private String createdBy;
    private List<RequestHoldiayDTO> holidays; // 👈 ADD THIS
}
