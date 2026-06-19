package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.MonthDto;
import Global.Holiday.Global.Holiday.admin.entities.Month;
import Global.Holiday.Global.Holiday.admin.mappers.MonthMapper;
import Global.Holiday.Global.Holiday.admin.repositories.MonthRepository;
import Global.Holiday.Global.Holiday.admin.services.MonthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthServiceImpl implements MonthService {
    private final MonthRepository repository;

    public MonthServiceImpl(MonthRepository repository) {
        this.repository = repository;
    }

    @Override
    public MonthDto getById(Long id) {
        Month entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Month not found"));
        return MonthMapper.toDTO(entity);
    }

    @Override
    public List<MonthDto> getAll() {
        return repository.findAll()
                .stream()
                .map(MonthMapper::toDTO)
                .toList();
    }
}
