package renewal.gym.service;

import jakarta.validation.constraints.NotNull;
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
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

//    public List<GymListDto> findAllGym(){
//        return gymRepository.findGymList();
//    }

    public List<GymListDto> findGymNames(Set<Long> gymIds) {
        return gymRepository.findGymNames(gymIds);
    }

    public Long findSelectedGym(String gymName, String address, String roadAddress) {
        return gymRepository.findByGymNameAndAddress(gymName, address, roadAddress).orElse(null);
    }

    public Integer findGymPriceById(Long gymId) {
        return gymRepository.findGymPriceById(gymId);
    }
}
