package top.camsyn.store.file.controller;



import top.camsyn.store.file.dto.UploadFileResponse;
import top.camsyn.store.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;



    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFileByURL")
    public UploadFileResponse uploadFileByURL(@RequestParam("url") String url_s, @RequestParam(name="id", required = false, defaultValue = "-1") String id){
        String fileName = fileService.storeFileByURL(url_s, id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        return new UploadFileResponse(fileName, fileDownloadUri, "", -1);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<UploadFileResponse> list = new ArrayList<>();
        if (files != null) {
            for (MultipartFile multipartFile:files) {
                UploadFileResponse uploadFileResponse = uploadFile(multipartFile);
                list.add(uploadFileResponse);
            }
        }
        return list;
    }

    @PostMapping("/uploadMultipleFilesByURL")
    public List<UploadFileResponse> uploadMultipleFilesByURL(@RequestParam("urls") String[] urls, @RequestParam(name="ids", required = false, defaultValue = "-1") String ids) {
        List<UploadFileResponse> list = new ArrayList<>();
        if (urls != null) {
            for (String url:urls) {
                UploadFileResponse uploadFileResponse = uploadFileByURL(url, ids);
                list.add(uploadFileResponse);
            }
        }
        return list;
    }

    @GetMapping("/downloadFile/{fileName:.*}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
