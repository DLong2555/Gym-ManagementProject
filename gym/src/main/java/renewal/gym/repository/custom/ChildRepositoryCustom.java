package renewal.gym.repository.custom;

import renewal.gym.dto.event.MyChildNames;
import renewal.gym.dto.mypage.MyChildForm;

import java.util.List;

public interface ChildRepositoryCustom {

    List<MyChildForm> findByMemberId(Long memberId);
    List<MyChildNames> findChildNamesByMemberIdAndGymId(Long memberId, Long gymId);
}
