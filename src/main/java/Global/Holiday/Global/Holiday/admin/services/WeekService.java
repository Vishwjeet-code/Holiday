package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.WeekDto;

import java.util.List;

public interface WeekService {
    WeekDto getById(Long id);

    List<WeekDto> getAll();
}
