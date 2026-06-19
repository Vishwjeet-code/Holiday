package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.StateDto;
import Global.Holiday.Global.Holiday.admin.entities.State;
import Global.Holiday.Global.Holiday.admin.mappers.StateMapper;
import Global.Holiday.Global.Holiday.admin.repositories.StateRepository;
import Global.Holiday.Global.Holiday.admin.services.StateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository repository;

    public StateServiceImpl(StateRepository repository) {
        this.repository = repository;
    }

    @Override
    public StateDto getById(Long id) {
        State entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found"));
        return StateMapper.toDTO(entity);
    }

    @Override
    public List<StateDto> getAll() {
        return repository.findAll()
                .stream()
                .map(StateMapper::toDTO)
                .toList();
    }
}
