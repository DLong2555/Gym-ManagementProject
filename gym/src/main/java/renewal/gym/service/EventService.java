package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Child;
import renewal.gym.domain.Event;
import renewal.gym.domain.EventChild;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.EventPayForm;
import renewal.gym.dto.event.MyChildNames;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.EventChildRepository;
import renewal.gym.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ChildRepository childRepository;
    private final EventChildRepository eventChildRepository;

    @Transactional
    public Long save(Event event) {
        return eventRepository.save(event).getId();
    }

    public EventInfoForm getEventInfo(Long boardId) {
        return eventRepository.findEventInfoById(boardId);
    }

    public List<MyChildNames> getChildNames(Long memberId, Long gymId, Long eventId) {
        return childRepository.findChildNamesByMemberIdAndGymId(memberId, gymId, eventId);
    }

    @Transactional
    public void saveApplication(EventPayForm eventPayForm) {
        Event event = eventRepository.findEventById(eventPayForm.getBoardId()).orElse(null);

        for (Long childId : eventPayForm.getChildIds()) {
            Child child = childRepository.findById(childId).orElse(null);

            if (child != null) {
                eventChildRepository.save(new EventChild(event, child));
            }
        }
    }
}
