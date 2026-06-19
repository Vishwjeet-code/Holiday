package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.QuarterDto;

import java.util.List;

public interface QuarterService {
    QuarterDto getById(Long id);

    List<QuarterDto> getAll();

}
