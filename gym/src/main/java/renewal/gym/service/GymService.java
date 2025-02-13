package renewal.gym.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Gym;
import renewal.gym.dto.GymListDto;
import renewal.gym.dto.SelectedGymForm;
import renewal.gym.repository.GymRepository;

import java.util.List;
import java.util.Optional;

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

    public Long findSelectedGym(String gymName, String address) {
        return gymRepository.findByGymNameAndAddress(gymName, address).orElse(null);
    }

}
