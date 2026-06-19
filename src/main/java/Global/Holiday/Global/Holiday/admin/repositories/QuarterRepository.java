package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.Quarter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuarterRepository extends JpaRepository<Quarter, Long> {
    Optional<Quarter> findByQuarter(String quarter);
}
