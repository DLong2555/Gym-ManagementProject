package renewal.gym.repository.custom;

import renewal.gym.dto.event.EventInfoForm;
import renewal.gym.dto.event.ManageEventForm;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EventRepositoryCustom {

    EventInfoForm findEventInfoById(Long id);

    List<ManageEventForm> getEvents(Long gymId);
}
