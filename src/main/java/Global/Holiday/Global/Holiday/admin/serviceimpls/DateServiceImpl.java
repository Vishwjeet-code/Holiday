package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.DateDto;
import Global.Holiday.Global.Holiday.admin.entities.date;
import Global.Holiday.Global.Holiday.admin.mappers.DateMapper;

import Global.Holiday.Global.Holiday.admin.repositories.DateRepository;
import Global.Holiday.Global.Holiday.admin.services.DateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DateServiceImpl implements DateService {

    private final DateRepository repository;

    public DateServiceImpl(DateRepository repository) {
        this.repository = repository;
    }

    @Override
    public DateDto getById(Long id) {
        date entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Date not found"));
        return DateMapper.toDTO(entity);
    }

//    @Override
//    public List<DateDTO> getAll() {
//        return repository.findAll()
//                .stream()
//                .map(DateMapper::toDTO)
//                .toList();
//    }

    @Override
    public List<DateDto> getByYear(Long yearId) {
        return repository.findByYear_YearId(yearId)
                .stream()
                .map(DateMapper::toDTO)
                .toList();
    }


}
