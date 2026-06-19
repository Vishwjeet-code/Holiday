package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.ContinentDto;
import Global.Holiday.Global.Holiday.admin.entities.Continent;
import Global.Holiday.Global.Holiday.admin.mappers.ContinentMapper;
import Global.Holiday.Global.Holiday.admin.repositories.ContinentRepository;
import Global.Holiday.Global.Holiday.admin.services.ContinentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContinentServiceImpl   implements ContinentService {

    private final ContinentRepository repository;

    public ContinentServiceImpl(ContinentRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContinentDto getById(Long id) {
        Continent entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
        return ContinentMapper.toDTO(entity);
    }

    @Override
    public List<ContinentDto> getAll() {
        return repository.findAll()
                .stream()
                .map(ContinentMapper::toDTO)
                .toList();
    }
}
