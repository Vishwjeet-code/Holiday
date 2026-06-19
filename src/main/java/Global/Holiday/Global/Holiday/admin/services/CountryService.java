package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.CountryDto;

import java.util.List;

public interface CountryService {



    List<CountryDto> getAllCountries();

    CountryDto getCountryById(Long id);


}
