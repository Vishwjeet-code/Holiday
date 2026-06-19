package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.WeekDto;
import Global.Holiday.Global.Holiday.admin.entities.Week;
import Global.Holiday.Global.Holiday.admin.mappers.WeekMapper;
import Global.Holiday.Global.Holiday.admin.repositories.WeekRepository;
import Global.Holiday.Global.Holiday.admin.services.WeekService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekServiceImpl  implements WeekService {

    private final WeekRepository repository;

    public WeekServiceImpl(WeekRepository repository) {
        this.repository = repository;
    }

    @Override
    public WeekDto getById(Long id) {
        Week entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Week not found"));
        return WeekMapper.toDTO(entity);
    }

    @Override
    public List<WeekDto> getAll() {
        return repository.findAll()
                .stream()
                .map(WeekMapper::toDTO)
                .toList();
    }
}
