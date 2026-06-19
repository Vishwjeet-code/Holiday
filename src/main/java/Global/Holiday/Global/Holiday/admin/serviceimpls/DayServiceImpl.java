package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.DayDto;
import Global.Holiday.Global.Holiday.admin.entities.Day;
import Global.Holiday.Global.Holiday.admin.mappers.DayMapper;
import Global.Holiday.Global.Holiday.admin.repositories.DayRepository;
import Global.Holiday.Global.Holiday.admin.services.DayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayServiceImpl implements DayService {

    private final DayRepository repository;

    public DayServiceImpl(DayRepository repository) {
        this.repository = repository;
    }

    @Override
    public DayDto getById(Long id) {
        Day entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Day not found"));
        return DayMapper.toDTO(entity);
    }

    @Override
    public List<DayDto> getAll() {
        return repository.findAll()
                .stream()
                .map(DayMapper::toDTO)
                .toList();
    }
}
