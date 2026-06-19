package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
