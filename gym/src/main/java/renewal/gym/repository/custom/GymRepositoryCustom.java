package renewal.gym.repository.custom;

import renewal.gym.dto.GymListDto;

import java.util.List;

public interface GymRepositoryCustom {

    List<GymListDto> findGymList();

}
