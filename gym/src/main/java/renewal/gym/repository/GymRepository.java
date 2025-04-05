package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import renewal.gym.domain.Gym;
import renewal.gym.repository.custom.GymRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long>, GymRepositoryCustom {

    List<Gym> findByManagerId(Long id);

    @Query("select g.id from Gym g where g.gymName = :gymName " +
            "and concat(g.address.roadName, ' ', g.address.detailAddress) LIKE concat('%', :address, '%') " +
            "or concat(g.address.roadName, ' ', g.address.detailAddress) LIKE concat('%', :roadAddress, '%')")
    Optional<Long> findByGymNameAndAddress(String gymName, String address, String roadAddress);

    @Query("select g.gymName from Gym g where g.id = :id")
    Optional<String> findGymNameById(Long id);

    @Query("select g.gymPrice from Gym g where g.id = :id")
    Optional<Integer> findGymPriceById(Long id);
}

