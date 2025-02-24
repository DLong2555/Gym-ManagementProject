package renewal.gym.repository.custom;

import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.manage.ParentsInfoForm;

import java.util.List;
import java.util.Map;

public interface ManagerRepositoryCustom {

   Map<String, List<ParentsInfoForm>> getChildInfo(List<Long> gymIds);
   Map<String, List<ParentsInfoForm>> getChildInfo2(List<Long> gymIds);

   LoginDTO getLoginInfo(String loginId);
}
