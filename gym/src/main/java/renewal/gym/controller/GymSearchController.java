package renewal.gym.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import renewal.gym.dto.ChildRegisterForm;
import renewal.gym.dto.NaverSearchResultForm;
import renewal.gym.dto.SearchForm;
import renewal.gym.service.naver.NaverSearchService;
import renewal.gym.service.GymService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/gym")
@RequiredArgsConstructor
public class GymSearchController {

    private final GymService gymService;
    private final NaverSearchService naverSearchService;

    //querydsl 방식
    @GetMapping("/search")
    public String gymListForm(Model model) {
        model.addAttribute("searchForm", new SearchForm());
        return "gym/gymSearchForm";
    }

    @ResponseBody
    @PostMapping("/search")
    public List<NaverSearchResultForm> gymListSubmit(@RequestBody SearchForm form) {
        log.debug("searchForm: {}", form.getSearchQuery());

        return naverSearchService.getSearchResult(form.getSearchQuery());
    }

    //    전체 불러오고 파싱
//    @GetMapping("/home2")
//    public List<GymListDto> home2(Model model) {
//
//        return gymService.findAllGymNames();
//
//    }
}
