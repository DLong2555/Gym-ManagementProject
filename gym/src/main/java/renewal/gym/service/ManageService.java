package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.ChildInfoForm;
import renewal.gym.repository.ChildRepository;
import renewal.gym.repository.GymRepository;
import renewal.gym.repository.ManagerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

    private final GymRepository gymRepository;
    private final ChildRepository childRepository;
    private final ManagerRepository managerRepository;

    public Map<String, Map<String, List<ChildInfoForm>>> findChildInMyGyms(List<Long> gymIds) {
//        Map<Gym, List<Child>> childrenMap = new HashMap<>();
//        for (Long gymId : gymIds) {
//            Gym gym = gymRepository.findById(gymId).orElseThrow(IllegalArgumentException::new);
//            List<Child> children = childRepository.findByGymId(gymId);
//
//            childrenMap.put(gym, children);
//        }

        Map<String, Map<String, List<ChildInfoForm>>> childrenMap = new HashMap<>();
        for (Long gymId : gymIds) {
            Map<String, List<ChildInfoForm>> childInfo = managerRepository.getChildInfo(gymId);
            String gymName = gymRepository.findGymNameById(gymId);

            childrenMap.put(gymName, childInfo);
        }

        log.debug("findChildInMyGyms: gymIds = {}", childrenMap);
        return childrenMap;
    }
}
