package renewal.gym.service;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.manage.EventParticipantForm;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.repository.ManagerRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

    private final ManagerRepository managerRepository;

    public List<ParentsInfoForm> findChildInMyGyms(Long gymId, String ctg) {
        List<ParentsInfoForm> childrenMap = managerRepository.getChildInfo(gymId, ctg);

        log.debug("findChildInMyGyms: gymIds = {}", childrenMap);
        return childrenMap;
    }

    public List<EventParticipantForm> getParticipants(Long eventId) {
        return managerRepository.getParticipants(eventId);
    }

}
