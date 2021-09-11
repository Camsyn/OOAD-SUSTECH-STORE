package top.camsyn.store.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import top.camsyn.store.test.service.MyService;
import top.camsyn.store.test.test.Demo;

@SpringBootConfiguration
public class CustomConfiguration {
    @Autowired
    Demo demo;

    @Autowired
    MyService myService;
}
