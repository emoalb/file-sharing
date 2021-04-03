package com.emosoft.cloud.filesharing.web.api;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;

@Controller
public class ApiController {

    public String uploadPath = "e:/uploads/";

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

            System.out.println("Error uploading " + file.getName());
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
                fileName = fileName.substring(0, fileName.length() - 2 - Integer.toString(count).length());
            }
            count++;
            return parseFile(new File(FilenameUtils.getFullPath(originalFilePath)
                    + fileName + "(" + count + ")."
                    + FilenameUtils.getExtension(originalFilePath)), count);
        }
        return originalFile;
    }

}
