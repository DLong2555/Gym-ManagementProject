package renewal.gym.repository.custom;

import renewal.gym.dto.GymListDto;
import renewal.gym.dto.mypage.MyGymForm;

import java.util.List;

public interface GymRepositoryCustom {

    List<GymListDto> findGymList();
    List<MyGymForm> findMyGymList(Long managerId);
}
