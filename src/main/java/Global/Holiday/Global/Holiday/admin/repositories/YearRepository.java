package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearRepository extends JpaRepository<Year, Long> {
    Optional<Year> findByYear(String year);
}
