package com.emosoft.cloud.filesharing.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {


    @GetMapping("/")
    public String getHomePage() {

        return "index";
    }


}
