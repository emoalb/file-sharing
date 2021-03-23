package com.emosoft.cloud.filesharing.domain.web;

//import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

//import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
//import java.util.List;

@Controller
public class HomeController {
public String uploadPath = "/uploads/";
    @GetMapping("/")
    public String getHomePage() {

        return "index";
    }

//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public ModelAndView submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
//        modelMap.addAttribute("file", file);
//
//        file.transferTo(new File("/uploads/" + file.getOriginalFilename()));
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("redirect:/");
//
//        return modelAndView;
//    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    @RequestMapping(path = "/api/uploadFile",
            method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("file", file);
        file.transferTo(parseFile(new File(uploadPath + file.getOriginalFilename()),0));
        System.out.println();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return ResponseEntity.ok().body("Ok");
    }

    File parseFile(File originalFile,int count) {
        File parsedFile = new File(originalFile.getAbsolutePath());

                if(parsedFile.exists()&& !parsedFile.isDirectory()){
                    count++;
                    return parseFile(new File(originalFile.getPath()+"("+count+")"),count);
                }

        return originalFile;
    }

}
