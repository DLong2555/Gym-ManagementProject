package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Event;
import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.MyChildNames;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.EventRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ChildRepository childRepository;

    public Long save(Event event) {
        return eventRepository.save(event).getId();
    }

    public EventInfoForm getEventInfo(Long boardId) {
        return eventRepository.findEventInfoById(boardId);
    }

    public List<MyChildNames> getChildNames(Long memberId, Long gymId) {
        return childRepository.findChildNamesByMemberIdAndGymId(memberId, gymId);
    }
}
