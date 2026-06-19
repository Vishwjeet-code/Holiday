package Global.Holiday.Global.Holiday.admin.services;

import Global.Holiday.Global.Holiday.admin.dtos.DateDto;

import java.util.List;

public interface DateService {

    DateDto getById(Long id);

//    List<DateDTO> getAll();


    List<DateDto> getByYear(Long yearId);
}
