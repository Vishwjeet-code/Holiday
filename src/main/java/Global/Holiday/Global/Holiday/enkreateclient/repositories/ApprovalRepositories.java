package Global.Holiday.Global.Holiday.enkreateclient.repositories;

import Global.Holiday.Global.Holiday.enkreateclient.entities.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ApprovalRepositories extends JpaRepository<Approval,Long> {
    Optional<Approval> findByStatus(String saved);


}
