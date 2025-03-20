package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import renewal.gym.domain.Child;
import renewal.gym.repository.custom.ChildRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long>, ChildRepositoryCustom {

    Optional<Child> findByGymIdAndMemberIdAndChildName(Long gymId, Long memberId, String childName);
    List<Child> findByGymId(Long gymId);

    Optional<Child> findByMemberIdAndChildName(Long id, String childName);

    @Modifying
    @Query("delete from Child c where c.member.id = :id and c.childName = :name")
    void deleteByMemberAndChildName(Long id, String name);

    @Query("select c from Child c where c.id in :childIds")
    List<Child> findChildByIds(List<Long> childIds);

}
