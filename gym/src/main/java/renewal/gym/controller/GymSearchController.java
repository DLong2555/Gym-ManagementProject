package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import renewal.gym.dto.map.NaverSearchResultForm;
import renewal.gym.dto.SearchForm;
import renewal.gym.dto.SelectedGymForm;
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
        model.addAttribute("selectedGym", new SelectedGymForm());
        return "gym/gymSearchForm";
    }

    @ResponseBody
    @PostMapping("/search")
    public List<NaverSearchResultForm> gymListSubmit(@RequestBody SearchForm form) {
        log.debug("searchForm: {}", form.getSearchQuery());

        return naverSearchService.getSearchResult(form.getSearchQuery());
    }

}
