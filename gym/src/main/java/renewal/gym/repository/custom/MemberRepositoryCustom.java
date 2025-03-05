package renewal.gym.repository.custom;

import renewal.gym.dto.mypage.MyPageForm;
import renewal.gym.dto.register.ParentInfoForm;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Long> findMyMemberGymLists(List<Long> members);

    MyPageForm getMyPageForm(Long id);

    ParentInfoForm getParentInfoForm(Long id);
}
