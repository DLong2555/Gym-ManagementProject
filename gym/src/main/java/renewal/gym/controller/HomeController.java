package renewal.gym.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import renewal.gym.error.CustomServiceException;
import renewal.gym.error.DataNotFoundException;
import renewal.gym.error.ExternalApiException;


@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(){

        return "home";
    }

    @GetMapping("/test")
    public void error(){
        throw new DataNotFoundException("해당 데이터를 찾을 수 없습니다");
    }

    @GetMapping("/test1")
    public void error2(){
        throw new CustomServiceException("해당 데이터를 찾을 수 없습니다");
    }

    @GetMapping("/test2")
    public void error3(){
        throw new ExternalApiException("해당 데이터를 찾을 수 없습니다");
    }

    @GetMapping("/test3")
    public void error4(){
        throw new RuntimeException("해당 데이터를 찾을 수 없습니다");
    }

}
