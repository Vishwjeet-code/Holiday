package Global.Holiday.Global.Holiday.admin.repositories;

import Global.Holiday.Global.Holiday.admin.entities.date;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DateRepository extends JpaRepository<date,Long> {

    List<date> findByYear_YearId(Long yearId);

    Optional<date> findByDate(LocalDate string);
    Optional<date> findByDateAndYear_YearId(LocalDate date, Long yearId);


}
