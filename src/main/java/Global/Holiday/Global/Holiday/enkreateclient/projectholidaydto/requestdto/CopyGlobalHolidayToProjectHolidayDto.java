package Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto;

import lombok.Data;

import java.util.List;

@Data
public class CopyGlobalHolidayToProjectHolidayDto {
    private Long projectId;
    private Long countryId;
    private Long yearId;
    private String createdBy;
    private List<Long> holidayIds;
}
