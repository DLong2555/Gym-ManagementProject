package renewal.gym.repository.custom;

import renewal.gym.dto.mypage.MyChildForm;

import java.util.List;

public interface ChildRepositoryCustom {

    List<MyChildForm> findByMemberId(Long memberId);
}
