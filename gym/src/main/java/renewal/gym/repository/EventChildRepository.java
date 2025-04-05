package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.EventChild;

public interface EventChildRepository extends JpaRepository<EventChild, Long> {
}
