package renewal.gym.repository.custom;

import renewal.gym.dto.GymListDto;
import renewal.gym.dto.mypage.MyGymForm;

import java.util.List;
import java.util.Set;

public interface GymRepositoryCustom {

//    List<GymListDto> findGymList();
    List<MyGymForm> findMyGymList(Long managerId);

    List<GymListDto> findGymNames(Set<Long> gymIds);
}
