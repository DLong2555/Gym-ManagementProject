package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Child;

public interface ChildRepository extends JpaRepository<Child, Long> {
}
