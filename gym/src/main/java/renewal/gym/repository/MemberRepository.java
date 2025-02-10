package renewal.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import renewal.gym.domain.Member;
import renewal.gym.repository.custom.MemberSessionRepositoryCustom;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberSessionRepositoryCustom {

    Optional<Member> findByMemId(String memId);

}
