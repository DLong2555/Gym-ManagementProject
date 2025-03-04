package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Child;
import renewal.gym.repository.custom.ChildRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long>, ChildRepositoryCustom {

    Optional<Child> findByGymIdAndMemberIdAndChildName(Long gymId, Long memberId, String childName);
    List<Child> findByGymId(Long gymId);

}
