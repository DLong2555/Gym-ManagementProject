package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Event;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.repository.custom.EventRepositoryCustom;

public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

}
