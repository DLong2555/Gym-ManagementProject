package renewal.gym.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import renewal.gym.dto.board.BoardEditForm;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.dto.board.BoardContentForm;

public interface BoardRepositoryCustom {

    Page<BoardInfoForm> findBoardByGymId(Long gymId, Pageable pageable);

    BoardContentForm findContentFormById(Long boardId);

    BoardEditForm findEditFormById(Long id);
}
