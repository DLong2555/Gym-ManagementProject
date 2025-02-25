package renewal.gym.repository.custom;

import renewal.gym.dto.MyPageForm;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Long> findMyMemberGymLists(List<Long> members);

    MyPageForm getMyPageForm(Long id);
}
