package Global.Holiday.Global.Holiday.enkreateclient.repositories;

import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectHoliday;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProjectHolidayRepository extends JpaRepository<ProjectHoliday,Long> {
    boolean existsByCityIdAndIndustryIdAndHolidayId(Long cityId, Long industryId, Long holidayId);

    List<ProjectHoliday> findByCityIdAndIndustryId(Long cityId, Long industryId);

    boolean existsByProjectIdAndHolidayId(Long projectId, Long holidayId);

    Optional<ProjectHoliday> findByHolidayId(Long holidayId);
}
