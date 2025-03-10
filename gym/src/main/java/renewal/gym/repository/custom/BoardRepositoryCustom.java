package renewal.gym.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import renewal.gym.dto.board.BoardInfoForm;

public interface BoardRepositoryCustom {

    Page<BoardInfoForm> findBoardByManagerId(Long managerId, Long gymId, Pageable pageable);
}
