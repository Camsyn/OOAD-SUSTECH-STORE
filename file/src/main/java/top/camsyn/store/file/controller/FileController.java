package top.camsyn.store.file.controller;



import lombok.extern.slf4j.Slf4j;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;



    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        log.info("开始上传文件 fileName: {}", file.getName());
        String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        log.info("上传成功");
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadFileByURL")
    public UploadFileResponse uploadFileByURL(@RequestParam("url") String url_s, @RequestParam(name="id", required = false, defaultValue = "-1") String id){
        log.info("开始通过url上传文件 url: {}", url_s);
        String fileName = fileService.storeFileByURL(url_s, id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        log.info("上传成功");
        return new UploadFileResponse(fileName, fileDownloadUri, "", -1);
    }

    @PostMapping("/downloadFileByURL")
    public File downloadFileByURL(@RequestParam("url") String url_s) throws Exception {
        log.info("通过url下载文件 url: {}", url_s);
        return fileService.downloadFileByURL(url_s);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        log.info("开始批量上传文件");
        List<UploadFileResponse> list = new ArrayList<>();
        if (files != null) {
            for (MultipartFile multipartFile:files) {
                UploadFileResponse uploadFileResponse = uploadFile(multipartFile);
                list.add(uploadFileResponse);
            }
        }
        log.info("批量上传成功");
        return list;
    }

    @PostMapping("/uploadMultipleFilesByURL")
    public List<UploadFileResponse> uploadMultipleFilesByURL(@RequestParam("urls") String[] urls, @RequestParam(name="ids", required = false, defaultValue = "-1") String ids) {
        log.info("开始通过url批量上传文件");
        List<UploadFileResponse> list = new ArrayList<>();
        if (urls != null) {
            for (String url:urls) {
                UploadFileResponse uploadFileResponse = uploadFileByURL(url, ids);
                list.add(uploadFileResponse);
            }
        }
        log.info("批量上传成功");
        return list;
    }

    @PostMapping("/downloadMultipleFilesByURL")
    public List<File> downloadMultipleFilesByURL(@RequestParam("url") String[] urls) throws Exception {
        log.info("开始通过url批量下载文件");
        List<File> list = new ArrayList<>();
        if(urls != null){
            for (String url:urls) {
                File file = fileService.downloadFileByURL(url);
                list.add(file);
            }
        }
        log.info("批量下载成功");
        return list;
    }

    @GetMapping("/downloadFile/{fileName:.*}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        log.info("开始下载文件");
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
        log.info("成功");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
