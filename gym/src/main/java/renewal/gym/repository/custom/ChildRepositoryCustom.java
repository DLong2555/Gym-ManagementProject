package renewal.gym.repository.custom;

import renewal.gym.dto.mypage.myChildForm;

import java.util.List;

public interface ChildRepositoryCustom {

    List<myChildForm> findByMemberId(Long memberId);
}
