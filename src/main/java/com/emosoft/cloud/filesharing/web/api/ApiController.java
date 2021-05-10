package com.emosoft.cloud.filesharing.web.api;

import com.emosoft.cloud.filesharing.services.models.FilePropertiesServiceModel;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    public String uploadPath = "/uploads/";
    private Gson gson;
    @Autowired
    public ApiController(Gson gson) {
        this.gson = gson;
    }



    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    @RequestMapping(path = "/uploadFile",
            method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws IOException {
        modelMap.addAttribute("file", file);

        try {
            Files.createDirectories(Paths.get(uploadPath));
            file.transferTo(parseFile(new File(uploadPath + file.getOriginalFilename()), 0));
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/");
            return ResponseEntity.ok().body("Ok");
        } catch (Exception e) {

            System.out.println("Error uploading " + file.getName());
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    @RequestMapping(path = "/getFileList", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getFileList() throws InterruptedException {
        List<String> pathNames = new ArrayList<String>();
      //  Thread.sleep(4000);
        File f = new File(uploadPath);
        if (f.list() != null) {
            pathNames = Arrays.asList(f.list());
        }
        return new ResponseEntity<>(pathNames, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ResponseBody
    @RequestMapping(path = "/getFileListGson", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getFileListGson() throws InterruptedException {
        List<String> pathNames = new ArrayList<String>();
        List<FilePropertiesServiceModel> filePropertiesServiceModels  = new ArrayList<>();
     //   Thread.sleep(4000);
        File f = new File(uploadPath);
        if (f.list() != null) {
//            Arrays.stream(f.list())..forEach( ff->{
//FilePropertiesServiceModel filePropertiesServiceModel = new FilePropertiesServiceModel();
//filePropertiesServiceModel.setFname(ff.);
//                filePropertiesServiceModel.setSize(ff.get);
//            });
            pathNames = Arrays.asList(f.list());
        }


        return new ResponseEntity<>(pathNames, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(path = "/downloadFile/{fileName}", method = RequestMethod.GET)
    public void getDownloadFile(HttpServletRequest req, HttpServletResponse res, @PathVariable("fileName") String fileName) throws InterruptedException {
        Path file = Paths.get(uploadPath, fileName);
        if (Files.exists(file)) {
            res.setContentType("application/octet-stream");
            res.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            //  res.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            try {
                Files.copy(file, res.getOutputStream());
                res.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
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
