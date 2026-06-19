package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.YearDto;
import Global.Holiday.Global.Holiday.admin.entities.Year;
import Global.Holiday.Global.Holiday.admin.mappers.YearMapper;
import Global.Holiday.Global.Holiday.admin.repositories.YearRepository;
import Global.Holiday.Global.Holiday.admin.services.YearService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearServiceImpl implements YearService {

    private final YearRepository repository;

    public YearServiceImpl(YearRepository repository) {
        this.repository = repository;
    }

    @Override
    public YearDto getById(Long id) {
        Year entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Year not found"));
        return YearMapper.toDTO(entity);
    }

    @Override
    public List<YearDto> getAll() {
        return repository.findAll()
                .stream()
                .map(YearMapper::toDTO)
                .toList();
    }
}
