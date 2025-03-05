package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.repository.ManagerRepository;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ManageService {

    private final ManagerRepository managerRepository;

    public Map<String, List<ParentsInfoForm>> findChildInMyGyms(List<Long> gymIds) {
        Map<String, List<ParentsInfoForm>> childrenMap = managerRepository.getChildInfo(gymIds);

        log.debug("findChildInMyGyms: gymIds = {}", childrenMap);
        return childrenMap;
    }

    public Map<String, List<ParentsInfoForm>> findChildInMyGyms2(List<Long> gymIds) {
        return managerRepository.getChildInfo2(gymIds);
    }

}
