package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.Month;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface MonthRepository extends JpaRepository<Month, Long> {
    Optional<Month> findByMonth(String month);
}
