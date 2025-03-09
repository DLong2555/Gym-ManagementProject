package renewal.gym.repository.custom;

import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.register.ParentInfoForm;

import java.util.List;
import java.util.Set;

public interface MemberRepositoryCustom {
    List<Long> findMyMemberGymLists(List<Long> members);

    MyPageForm getMyPageForm(Long id);

    ParentInfoForm getParentInfoForm(Long id);

    Set<Long> getMyGymList(Long id);
}
