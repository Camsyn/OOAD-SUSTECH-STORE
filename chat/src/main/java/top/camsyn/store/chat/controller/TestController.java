package top.camsyn.store.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.helper.UaaHelper;

@RestController
@Slf4j
public class TestController {

    @RequestMapping("/demo1")
    public String test() {
        return UaaHelper.getLoginSid() + "";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
