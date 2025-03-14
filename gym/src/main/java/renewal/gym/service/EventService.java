package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Event;
import renewal.gym.repository.EventRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Long save(Event event) {
        return eventRepository.save(event).getId();
    }
}
