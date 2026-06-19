package Global.Holiday.Global.Holiday.admin.serviceimpls;

import Global.Holiday.Global.Holiday.admin.dtos.CountryDto;
import Global.Holiday.Global.Holiday.admin.entities.Country;
import Global.Holiday.Global.Holiday.admin.mappers.CountryMapper;
import Global.Holiday.Global.Holiday.admin.repositories.CountryRepository;
import Global.Holiday.Global.Holiday.admin.services.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    public CountryServiceImpl(CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CountryDto getCountryById(Long id) {
        Country entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country not found"));
        return CountryMapper.toDTO(entity);
    }

    @Override
    public List<CountryDto> getAllCountries() {
        return repository.findAll()
                .stream()
                .map(CountryMapper::toDTO)
                .toList();
    }
}
