package Global.Holiday.Global.Holiday.enkreateclient.services;

import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.ProjectworkingDaysRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.responsedto.ProjectworkingDaysResponseDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.updatedto.ProjectWorkingDaysUpdateDto;

import java.util.List;

public interface ProjectWorkingDaysService {
    void saveProjectWorkingDasy(ProjectworkingDaysRequestDto projectworkingDaysRequestDto);

    List<ProjectworkingDaysResponseDto> getAll(Long projectId);

    void updateProjectWorkingDays(ProjectWorkingDaysUpdateDto projectWorkingDaysUpdateDto);
}
