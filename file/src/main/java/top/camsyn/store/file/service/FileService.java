package top.camsyn.store.file.service;

import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.file.excepiton.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;


@Component
public class FileService {
    private Path fileStorageLocation; // 文件在本地存储的地址

    public String getTime(){
        LocalDateTime datetime = LocalDateTime.now();
        return "" + datetime.getYear()+datetime.getMonthValue()+datetime.getDayOfMonth()+datetime.getHour()+datetime.getMinute()+datetime.getSecond()+datetime.getNano();
    }

    public FileService( @Value("${file.upload.path}") String path) {
        this.fileStorageLocation = Paths.get(path).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileStorageLocation.resolve("cache"));
        } catch (IOException e) {
            throw new FileException("Could not create the directory", e);
        }
    }

    /**
     * 存储文件到系统
     * @param file 文件
     * @return 文件名
     */
    public String storeFile(MultipartFile file) {
        // Normalize file name
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String[] name = file.getOriginalFilename().split("\\.");
        // 重新命名为学号加当前时间
//        String owner = "11913005";
        int owner = UaaHelper.getLoginSid();
        String fileName = owner + "_" + getTime() + "." + name[name.length-1];



        try {
//            // Check if the file's name contains invalid characters
//            if(fileName.contains("..")) {
//                throw new FileException("Sorry! Filename contains invalid path sequence " + fileName);
//            }


            String type = file.getContentType();
            long size = file.getSize() / 1024;
            if (type.split("/")[0].equals("image") && size > 5120) throw new FileException("file too big");
            if (type.split("/")[0].equals("video") && size > 204800) throw new FileException("file too big");

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            if (ex.getMessage().equals("file too big")) throw new FileException("file too big: " + fileName, ex);
            else throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFileByURL(String url_s, String id_s) {
        int id = Integer.parseInt(id_s);
        String[] label = url_s.split("\\.");
        String[] name = url_s.split("/");

//        String owner = "11913005";
        int owner = UaaHelper.getLoginSid();
        String fileName = owner + "_" + getTime() + "." + label[label.length-1];
//        String fileName = name[name.length-1].replace("."+label[label.length-1], "_") + getTime() + "." + label[label.length-1];

        if(id>=0) {
            String path = "";
            for (int i = 0; i < name.length - 1; i++) {
                path += name[i] + "/";
            }
            url_s = path + "cache/" + name[name.length - 1];
            fileName = name[name.length - 1];
        }
        //根据url获取输入流
        try {
            URL url = new URL(url_s);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //得到输入流
            InputStream inputStream = conn.getInputStream();
            Path targetLocation = this.fileStorageLocation;
            if(id==-1) {
                targetLocation = targetLocation.resolve("cache").resolve(fileName);
            }else if(id==-2 || id>=0){
                targetLocation = targetLocation.resolve(fileName);
            }else {
                throw new FileException("wrong id");
            }
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            if (ex.getMessage().equals("wrong id")) throw new FileException("wrong id: " + id, ex);
            else throw new FileException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    public File downloadFileByURL(String url) throws Exception{
        //对本地文件命名
        String[] label = url.split("\\.");
        String[] name = url.split("/");

        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("pre"+name[name.length-1], label[label.length-1]);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;

    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileException("File not found " + fileName);
            }
        } catch (MalformedURLException  ex) {
            throw new FileException("File not found " + fileName, ex);
        }
    }
}
