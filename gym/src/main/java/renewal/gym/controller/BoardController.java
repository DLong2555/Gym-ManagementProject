package renewal.gym.controller;


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
import renewal.gym.domain.Role;
import renewal.gym.dto.GymInfoDto;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.dto.board.BoardInfoForm;
import renewal.gym.dto.board.BoardWriteForm;
import renewal.gym.dto.board.UploadDto;
import renewal.gym.service.BoardService;
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
    private final UploadService uploadService;

    @GetMapping("/{gymId}")
    public String board(@PathVariable(value = "gymId") Long gymId,
                        @PageableDefault(size = 3, page = 0) Pageable pageable,
                        @Login LoginUserSession userSession, Model model) {

        List<GymInfoDto> gymNames = gymService.findGymNames(userSession.getGymIds());

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

    @PostMapping("/write")
    public String writeForm(@ModelAttribute("gymInfo") GymInfoDto gymInfo, Model model) {

        log.info("gymInfo : {}", gymInfo);

        model.addAttribute("writeForm", new BoardWriteForm());

        return "board/writeForm";
    }

    @PostMapping("/write/save")
    public String save(@ModelAttribute("writeForm") BoardWriteForm writeForm, Model model) {

        log.info("writeForm {}", writeForm);

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity<List<UploadDto>> upload(@RequestParam List<MultipartFile> files){
        return ResponseEntity.ok(uploadService.uploadFiles(files));
    }


    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource getImage(@PathVariable(value = "fileName") String fileName) throws MalformedURLException {
        String s = "file:" + uploadService.getFullPath(fileName);
        log.info("fileName : {}", s);
        return new UrlResource(s);
    }

}
