package com.emosoft.cloud.filesharing.domain.web;

//import org.springframework.http.MediaType;

import org.apache.commons.io.FilenameUtils;
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
    public String uploadPath = "e:/uploads/";

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

        try {
            file.transferTo(parseFile(new File(uploadPath + file.getOriginalFilename()), 0));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/");
            return ResponseEntity.ok().body("Ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        //  System.out.println();

    }

    File parseFile(File originalFile, int count) {
        String originalFilePath = originalFile.getPath();
        File parsedFile = new File(originalFile.getAbsolutePath());

        if (parsedFile.exists() && !parsedFile.isDirectory()) {
            String fileName = FilenameUtils.getBaseName(originalFilePath);
            if (count != 0) {
                Integer cnt = new Integer(count);
                fileName = fileName.substring(0, fileName.length() - 2 - cnt.toString().length());
            }
            count++;
            return parseFile(new File(FilenameUtils.getFullPath(originalFilePath)
                    + fileName + "(" + count + ")."
                    + FilenameUtils.getExtension(originalFilePath)), count);
        }
        return originalFile;
    }

}
