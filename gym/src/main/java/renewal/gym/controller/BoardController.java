package renewal.gym.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import renewal.gym.controller.argument.Login;
import renewal.gym.domain.*;
import renewal.gym.dto.GymInfoDto;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.board.*;

import renewal.gym.repository.EventRepository;
import renewal.gym.repository.ManagerRepository;
import renewal.gym.service.BoardService;
import renewal.gym.service.EventService;
import renewal.gym.service.GymService;
import renewal.gym.service.UploadService;

import java.net.MalformedURLException;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gym/board")
@RequiredArgsConstructor
public class BoardController {

    @Value("${file.dir}")
    private String fileDir;

    private final BoardService boardService;
    private final GymService gymService;
    private final ManagerRepository managerRepository;
    private final UploadService uploadService;
    private final EventService eventService;

    @GetMapping("/{gymId}")
    public String board(@PathVariable(value = "gymId") Long gymId,
                        @PageableDefault(size = 3, page = 0) Pageable pageable,
                        @Login LoginUserSession userSession, Model model) {

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getGymIds());

        Page<BoardInfoForm> findBoardPage = boardService.findBoardByGymId(gymId, pageable);
        List<BoardInfoForm> contents = findBoardPage.getContent();
        int totalPages = findBoardPage.getTotalPages();

        model.addAttribute("boards", contents);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageable.getPageNumber());

        model.addAttribute("gymId", gymId);
        model.addAttribute("gymNames", gymNames);

        return "board/boardForm";
    }

    @GetMapping("/write/{id}")
    public String writeForm(@PathVariable("id") Long gymId, Model model) {

        model.addAttribute("gymName", gymService.findGymNameById(gymId));
        model.addAttribute("writeForm", new BoardWriteForm());

        return "board/writeForm";
    }

    @PostMapping("/write/{id}")
    public String save(@PathVariable("id") Long gymId, @ModelAttribute("writeForm") BoardWriteForm writeForm,
                       @Login LoginUserSession userSession, Model model) {

        log.info("writeForm {}", writeForm);
        log.info("ctg {} ", writeForm.getCtg());

        Manager manager = managerRepository.findById(userSession.getId()).orElse(null);
        Gym gym = gymService.findGymByGymId(gymId);

        Long boardId = 0L;
        if(writeForm.getCtg() == BoardCtg.EVENT){
            boardId = boardService.save(new Event(manager, gym, writeForm.getTitle(), writeForm.getContent(),
                    writeForm.getPrice(), writeForm.getDeadline()));
        }else {
            boardId = boardService.save(new Board(manager, gym, writeForm.getTitle(), writeForm.getContent()));
        }

        return "redirect:/gym/board/content/" + boardId;
    }


    @GetMapping("/content/{id}")
    public String contents(@PathVariable("id") Long boardId, HttpServletRequest request,
                           HttpServletResponse response,
                           @Login LoginUserSession userSession, Model model) {

        increaseViewCount(boardId, request, response, userSession);

        BoardContentForm boardContentForm = boardService.getContent(boardId);
        log.debug("boardContentForm {}", boardContentForm);
        model.addAttribute("contentForm", boardContentForm);

        return "board/contentForm";
    }

    private void increaseViewCount(Long boardId, HttpServletRequest request, HttpServletResponse response,
                                LoginUserSession userSession) {

        if(userSession.getRole() == Role.MANAGER) {
            return;
        }

        Cookie[] cookies = request.getCookies();
        String cookieName = userSession.getId() + "_" + boardId + "_cookie";
        boolean isViewed = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    isViewed = true;
                    break;
                }
            }
        }

        if (!isViewed) {
            log.info("view cookie create!");
            boardService.increaseViewCount(boardId);
            Cookie viewCookie = new Cookie(cookieName, "true");
            viewCookie.setMaxAge(60 * 60 * 24);
            viewCookie.setPath("/");
            response.addCookie(viewCookie);
        }
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long boardId, Model model) {

        BoardEditForm editForm = boardService.getEditForm(boardId);
        model.addAttribute("editForm", editForm);

        return "board/editForm";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long boardId, @ModelAttribute BoardEditForm editForm) {
        log.info("editForm {}", editForm);
        boardService.boardEdit(boardId, editForm);

        return "redirect:/gym/board/content/" + boardId;
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<List<UploadDto>> upload(@RequestParam List<MultipartFile> files) {
        return ResponseEntity.ok(uploadService.uploadFiles(files));
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource getImage(@PathVariable(value = "fileName") String fileName) throws MalformedURLException {
        return new UrlResource("file:" + uploadService.getFullPath(fileName));
    }

}
