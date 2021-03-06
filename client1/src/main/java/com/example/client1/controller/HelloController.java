package com.example.client1.controller;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.helper.UaaHelper;

import java.util.Arrays;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return UaaHelper.getLoginSid()+"";
    }
}
