package Global.Holiday.Global.Holiday.enkreateclient.services;

import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.CopyGlobalHolidayToProjectHolidayDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.requestdto.ProjectHolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectholidaydto.response.projectHolidayResponseDto;

import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.HolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.projectworkingdaysdto.RequestDto.ProjectworkingDaysRequestDto;

import java.util.List;

public interface ProjectHolidayService {
    void saveProjectHoliday(CopyGlobalHolidayToProjectHolidayDto projectHolidayRequestDto);



    List<projectHolidayResponseDto> getProjectHolidays(Long projectId, Long countryId, Long yearId);

    void saveholiday(ProjectHolidayRequestDto holidayRequestDto);

    void updateProjectHoliday(HolidayRequestDto request);


}
