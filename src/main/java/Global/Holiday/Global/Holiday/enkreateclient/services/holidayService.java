package Global.Holiday.Global.Holiday.enkreateclient.services;

import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.copydtos.CopyRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.requestdtos.HolidayRequestDto;
import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.responsedtos.HolidayResponseDto;

import java.util.List;

public interface holidayService {

    void saveholiday(HolidayRequestDto holdiay );

    List<HolidayResponseDto> getAllHolidays();

    List<HolidayResponseDto> getByCountryAndYear(Long countryId, Long yearId);

    void updateHoliday(HolidayRequestDto request);

    void copySelectedHolidays(CopyRequestDto request);
}
