package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import renewal.gym.domain.Gym;
import renewal.gym.repository.custom.GymRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long>, GymRepositoryCustom {

    List<Gym> findByManagerId(Long id);

    @Query("select g.id from Gym g where g.gymName = :gymName and CONCAT(g.address.roadName,' ', g.address.detailAddress) = :address")
    Optional<Long> findByGymNameAndAddress(String gymName, String address);
}
