package com.tuosresjours.calendar;

//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Slf4j
@Controller
public class HomeController {

    @GetMapping({"", "/"})
    public String home() {
     System.out.println("[HomeController] home()");
        return "home";
    }

}
