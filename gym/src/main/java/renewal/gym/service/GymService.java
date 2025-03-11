package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.dto.GymInfoDto;
import renewal.gym.repository.GymRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

//    public List<GymInfoDto> findAllGym(){
//        return gymRepository.findGymList();
//    }

    public List<GymInfoDto> findGymNames(Set<Long> gymIds) {
        return gymRepository.findGymNames(gymIds);
    }

    public Long findSelectedGym(String gymName, String address, String roadAddress) {
        return gymRepository.findByGymNameAndAddress(gymName, address, roadAddress).orElse(null);
    }

    public Integer findGymPriceById(Long gymId) {
        return gymRepository.findGymPriceById(gymId);
    }
}
