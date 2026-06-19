package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.Day;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DayRepository extends JpaRepository<Day, Long> {
    Optional<Day> findByDay(String day);
}
