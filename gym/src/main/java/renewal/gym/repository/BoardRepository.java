package renewal.gym.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Board;
import renewal.gym.repository.custom.BoardRepositoryCustom;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
