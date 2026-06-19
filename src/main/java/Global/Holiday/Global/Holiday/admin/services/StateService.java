package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.StateDto;

import java.util.List;

public interface StateService {

    StateDto getById(Long id);

    List<StateDto> getAll();

}
