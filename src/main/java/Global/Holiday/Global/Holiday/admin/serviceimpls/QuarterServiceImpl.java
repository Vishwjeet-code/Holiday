package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.QuarterDto;
import Global.Holiday.Global.Holiday.admin.entities.Quarter;
import Global.Holiday.Global.Holiday.admin.mappers.QuarterMapper;
import Global.Holiday.Global.Holiday.admin.repositories.QuarterRepository;
import Global.Holiday.Global.Holiday.admin.services.QuarterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuarterServiceImpl implements QuarterService {

    private final QuarterRepository repository;

    public QuarterServiceImpl(QuarterRepository repository) {
        this.repository = repository;
    }

    @Override
    public QuarterDto getById(Long id) {
        Quarter entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quarter not found"));
        return QuarterMapper.toDTO(entity);
    }

    @Override
    public List<QuarterDto> getAll() {
        return repository.findAll()
                .stream()
                .map(QuarterMapper::toDTO)
                .toList();
    }
}
