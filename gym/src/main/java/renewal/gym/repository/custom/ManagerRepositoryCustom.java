package renewal.gym.repository.custom;

import renewal.gym.dto.ChildInfoForm;
import renewal.gym.dto.LoginDTO;

import java.util.List;
import java.util.Map;

public interface ManagerRepositoryCustom {

   Map<String, List<ChildInfoForm>> getChildInfo(Long gymId);

   LoginDTO getLoginInfo(String loginId);
}
