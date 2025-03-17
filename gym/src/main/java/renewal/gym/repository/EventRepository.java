package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Event;
import renewal.gym.repository.custom.EventRepositoryCustom;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    Optional<Event> findEventById(Long boardId);
}
