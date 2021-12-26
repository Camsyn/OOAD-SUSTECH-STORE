package top.camsyn.store.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.file.service.FileService;

import java.io.File;


@SpringBootTest
class FileApplicationTests {

    @Autowired
    private FileService fileService;

    @Test
    void contextLoads() throws Exception {
        File file = fileService.downloadFileByURL("https://www.baidu.com/img/540-258_b7ccca84a418140d9ac666e10b82ac27.gif");
        System.out.println(file.getName());
        System.out.println(file.canRead());
        System.out.println(file.getAbsolutePath());
    }

}
