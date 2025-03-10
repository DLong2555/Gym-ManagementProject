package renewal.gym.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.repository.BoardRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Page<BoardInfoForm> findBoardByManagerId(Long managerId, Long gymId, Pageable pageable) {
        return boardRepository.findBoardByManagerId(managerId, gymId, pageable);
    }
}
