package renewal.gym.repository.custom;

import renewal.gym.dto.GymInfoDto;
import renewal.gym.dto.mypage.MyGymForm;

import java.util.List;
import java.util.Set;

public interface GymRepositoryCustom {

//    List<GymInfoDto> findGymList();
    List<MyGymForm> findMyGymList(Long managerId);

    List<GymInfoDto> findGymNames(Set<Long> gymIds);
}
