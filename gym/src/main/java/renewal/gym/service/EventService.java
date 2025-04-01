package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Child;
import renewal.gym.domain.Event;
import renewal.gym.domain.EventChild;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.EventPayForm;
import renewal.gym.dto.event.ManageEventForm;
import renewal.gym.dto.event.MyChildNames;
import renewal.gym.error.DataNotFoundException;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.EventChildRepository;
import renewal.gym.repository.EventRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Event event = eventRepository.findEventById(eventPayForm.getBoardId()).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));

        List<Child> children = childRepository.findChildByIds(eventPayForm.getChildIds());

        List<EventChild> eventChildren = children.stream()
                .map(child -> new EventChild(event, child))
                .collect(Collectors.toList());

        eventChildRepository.saveAll(eventChildren);
    }

    public List<ManageEventForm> getEvents(Long gymId) {
        return eventRepository.getEvents(gymId);
    }
}
