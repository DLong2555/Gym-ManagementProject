package renewal.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Gym;
import renewal.gym.dto.GymInfoDto;
import renewal.gym.error.DataNotFoundException;
import renewal.gym.repository.GymRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GymService {

    private final GymRepository gymRepository;

    public List<GymInfoDto> findGymNames(Long id) {
        return gymRepository.findGymNames(id);
    }

    public List<GymInfoDto> findGymNames(Set<Long> ids) {
        return gymRepository.findGymNames(ids);
    }

    public Long findSelectedGym(String gymName, String address, String roadAddress) {
        return gymRepository.findByGymNameAndAddress(gymName, address, roadAddress).orElse(null);
    }

    public Integer findGymPriceById(Long gymId) {
        return gymRepository.findGymPriceById(gymId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));
    }

    public Gym findGymByGymId(Long gymId) {
        return gymRepository.findById(gymId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));
    }

    public String findGymNameById(Long gymId) {
        return gymRepository.findGymNameById(gymId).orElseThrow(() -> new DataNotFoundException("해당 데이터를 찾을 수 없습니다."));
    }
}
