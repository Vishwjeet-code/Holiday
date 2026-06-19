package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.CityDto;


import java.util.List;

public interface CityService {



    CityDto getById(Long id);

    List<CityDto> getAll();

}
