package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.CityDto;
import Global.Holiday.Global.Holiday.admin.entities.City;
import Global.Holiday.Global.Holiday.admin.mappers.CityMapper;
import Global.Holiday.Global.Holiday.admin.repositories.CityRepository;
import Global.Holiday.Global.Holiday.admin.services.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository repository;

    public CityServiceImpl(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public CityDto getById(Long id) {
        City entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
        return CityMapper.toDTO(entity);
    }

    @Override
    public List<CityDto> getAll() {
        return repository.findAll()
                .stream()
                .map(CityMapper::toDTO)
                .toList();
    }
}
