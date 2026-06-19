package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.MonthDto;

import java.util.List;

public interface MonthService {
    MonthDto getById(Long id);

    List<MonthDto> getAll();
}
