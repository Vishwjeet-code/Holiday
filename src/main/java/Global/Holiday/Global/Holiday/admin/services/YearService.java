package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.YearDto;

import java.util.List;

public interface YearService {

    YearDto getById(Long id);

    List<YearDto> getAll();


}
