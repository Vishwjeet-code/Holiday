package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.ContinentDto;

import java.util.List;

public interface ContinentService {

    ContinentDto getById(Long id);

    List<ContinentDto> getAll();

}
