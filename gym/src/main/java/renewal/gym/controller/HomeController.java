package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import renewal.gym.constant.LoginSessionConst;
import renewal.gym.dto.GymListDto;
import renewal.gym.dto.LoginUserSession;
import renewal.gym.service.GymService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final GymService gymService;

    @GetMapping("/")
    public String index(){

        return "home";
    }

    @GetMapping("/test")
    public String test(){
        return "layout/test";
    }

//    전체 불러오고 파싱
//    @GetMapping("/home2")
//    public List<GymListDto> home2(Model model) {
//
//        return gymService.findAllGymNames();
//
//    }
}
