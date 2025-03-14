package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
