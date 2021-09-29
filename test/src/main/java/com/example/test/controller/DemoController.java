package com.example.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemoController {

    @RequestMapping("/")
    @ResponseBody
    public String hello(@RequestParam("name") String name){
        return "Hello "+name+"!";
    }
    @RequestMapping("/")
    @ResponseBody
    public String hi(@RequestParam("hi") String hi){
        return "Hi "+hi+"!";
    }
    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello world!";
    }

/*
    @RequestMapping("/send")
    @PreAuthorize("hasAnyRole('ROLE_selector')")
    public SendResult send() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        return mqProducer.send(new Message("verify", "mail", "test".getBytes()));
    }
*/

//    @RequestMapping("/login")
//    public String login(){
//        return "custom-login";
//
//    }
//    @GetMapping("/getCurrentUser")
//    public Object getCurrentUser(Authentication authentication) {
//        return authentication;
//    }
//    @GetMapping("/getUserInfoList")
//    @PostFilter("filterObject.userName == '小强'")
//    public List<UserInfo> getUserInfoList(){
//        List<UserInfo> list = new ArrayList<>();
//        list.add(new UserInfo("小明","123"));
//        list.add(new UserInfo("小强","123"));
//        list.add(new UserInfo("小红","123"));
//        return list;
//    }


}
