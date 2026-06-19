package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.IndustryDto;
import Global.Holiday.Global.Holiday.admin.entities.Industry;
import Global.Holiday.Global.Holiday.admin.mappers.IndustryMapper;
import Global.Holiday.Global.Holiday.admin.repositories.IndustryRepository;
import Global.Holiday.Global.Holiday.admin.services.IndustryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository repository;

    public IndustryServiceImpl(IndustryRepository repository) {
        this.repository = repository;
    }

    @Override
    public IndustryDto getById(Long id) {
        Industry entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Industry not found"));
        return IndustryMapper.toDTO(entity);
    }

    @Override
    public List<IndustryDto> getAll() {
        return repository.findAll()
                .stream()
                .map(IndustryMapper::toDTO)
                .toList();
    }
}
