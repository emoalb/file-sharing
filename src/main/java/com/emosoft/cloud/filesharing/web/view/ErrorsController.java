package com.emosoft.cloud.filesharing.web.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ErrorsController implements ErrorController {

    @GetMapping("/error")
    public ModelAndView getError() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.setViewName("errors");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
