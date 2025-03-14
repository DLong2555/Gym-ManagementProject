package renewal.gym.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import renewal.gym.domain.Board;
import renewal.gym.domain.BoardCtg;
import renewal.gym.domain.Event;
import renewal.gym.dto.board.BoardContentForm;
import renewal.gym.dto.board.BoardEditForm;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.repository.BoardRepository;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(Board board) {
        return boardRepository.save(board).getId();
    }

    public Page<BoardInfoForm> findBoardByGymId(Long gymId, Pageable pageable) {
        return boardRepository.findBoardByGymId(gymId, pageable);
    }

    public BoardContentForm getContent(Long boardId) {
        return boardRepository.findContentFormById(boardId);
    }

    @Transactional
    public void increaseViewCount(Long boardId) {
        log.debug("increase view count {} ", boardId);
        boardRepository.findBoardById((boardId)).ifPresent(Board::increaseViewCount);
    }

    public BoardEditForm getEditForm(Long boardId) {
        return boardRepository.findEditFormById(boardId);
    }

    @Transactional
    public void boardEdit(Long boardId, BoardEditForm editForm) {
        boardRepository.findBoardById((boardId)).ifPresent(board -> {
            if (editForm.getCtg() == BoardCtg.ANNOUNCEMENT) {
                board.updateBoard(editForm.getTitle(), editForm.getContent());
            }else{
                log.debug("board update ctg {} ", editForm.getCtg());
                Event event = (Event) board;
                event.updateEvent(editForm.getTitle(), editForm.getContent(), editForm.getPrice(), editForm.getDeadline());
            }
        });
    }
}
