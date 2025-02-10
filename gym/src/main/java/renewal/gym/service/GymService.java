package renewal.gym.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Gym;
import renewal.gym.dto.GymListDto;
import renewal.gym.repository.GymRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public List<GymListDto> findAllGym(){
        return gymRepository.findGymList();
    }

    public List<GymListDto> findAllGymNames() {
        return gymRepository.findAll()
                .stream()
                .map(gym -> new GymListDto(gym.getId(), gym.getGymName()))
                .toList();

    }

}
