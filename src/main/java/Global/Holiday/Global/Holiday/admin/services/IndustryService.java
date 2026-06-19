package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.IndustryDto;

import java.util.List;

public interface IndustryService {

    IndustryDto getById(Long id);

    List<IndustryDto> getAll();
}
