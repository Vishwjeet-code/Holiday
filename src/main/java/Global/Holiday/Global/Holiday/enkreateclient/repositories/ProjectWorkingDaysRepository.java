package Global.Holiday.Global.Holiday.enkreateclient.repositories;

import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectWorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProjectWorkingDaysRepository extends JpaRepository<ProjectWorkingDays,Long> {
    List<ProjectWorkingDays> findByProjectId(Long projectId);



    Optional<ProjectWorkingDays> findByProjectIdAndDayId(
            Long projectId,
            Long dayId
    );
}
