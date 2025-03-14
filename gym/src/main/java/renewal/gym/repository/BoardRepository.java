package renewal.gym.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import renewal.gym.domain.Board;
import renewal.gym.dto.board.BoardEditForm;
import renewal.gym.repository.custom.BoardRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Optional<Board> findBoardById(Long id);
}
