package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.DayDto;

import java.util.List;

public interface DayService {
    DayDto getById(Long id);

    List<DayDto> getAll();
}
