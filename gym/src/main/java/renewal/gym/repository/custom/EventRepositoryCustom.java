package renewal.gym.repository.custom;

import renewal.gym.dto.event.EventInfoForm;

public interface EventRepositoryCustom {
    EventInfoForm findEventInfoById(Long id);
}
