package renewal.gym.repository.custom;

import com.querydsl.core.Tuple;
import renewal.gym.dto.LoginDTO;
import renewal.gym.dto.manage.ParentsInfoForm;
import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.mypage.MyPageManagerForm;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ManagerRepositoryCustom {

//   Map<String, List<ParentsInfoForm>> getChildInfo(Set<Long> gymIds);
   List<ParentsInfoForm> getChildInfo(Long gymId, String ctg);

   MyPageManagerForm getMyPageForm(Long id);

   LoginDTO getLoginInfo(String loginId);
}
