package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.Role;
import renewal.gym.dto.GymListDto;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.service.BoardService;
import renewal.gym.service.GymService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final GymService gymService;

    @GetMapping("/board/{gymId}")
    public String board(@PathVariable(value = "gymId", required = false) Long gymId,
                        @PageableDefault(size = 3, page = 0) Pageable pageable,
                        @Login LoginUserSession userSession, Model model) {

        List<GymListDto> gymNames = gymService.findGymNames(userSession.getGymIds());

        if (userSession.getRole() == Role.MANAGER) {
            Page<BoardInfoForm> findBoardPage = boardService.findBoardByManagerId(userSession.getId(), gymId, pageable);
            List<BoardInfoForm> contents = findBoardPage.getContent();
            int totalPages = findBoardPage.getTotalPages();

            model.addAttribute("boards", contents);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("currentPage", pageable.getPageNumber());
        }

        model.addAttribute("gymId", gymId);
        model.addAttribute("gymNames", gymNames);

        return "board/boardForm";
    }

}
