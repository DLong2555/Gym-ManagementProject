package renewal.gym.repository.custom;

import java.util.List;

public interface MemberSessionRepositoryCustom {
    List<Long> findMyMemberGymLists(List<Long> members);
}
