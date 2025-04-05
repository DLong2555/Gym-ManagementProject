package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Manager;
import renewal.gym.repository.custom.ManagerRepositoryCustom;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, ManagerRepositoryCustom {

    Optional<Manager> findByManageId(String memId);

}
