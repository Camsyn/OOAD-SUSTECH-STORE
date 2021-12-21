package com.example.test.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.client.UserClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RestController
public class DemoController {

    static class Data{
        int id=1;
        LocalDateTime localDate = LocalDateTime.now();


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public LocalDateTime getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDateTime localDate) {
            this.localDate = localDate;
        }
    }

    @RequestMapping("/")
    @ResponseBody
    public Data hello(){
        return new Data();
    }


    @Autowired
    OrderClient orderClient;
    @Autowired
    UserClient userClient;

    @RequestMapping("/time")
    @ResponseBody
    public Data testTime(@RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time){
        System.out.println(time);
        Data data = new Data();
        if (time!=null)
        data.localDate = time;
        return data;
    }

    @GetMapping("/test/{id}")
    public String test(@PathVariable("id") int id){
        return orderClient.test(id);
    }

    @GetMapping("/test1/{id}")
    public String test1(@PathVariable("id") int id){
        return userClient.getUser(id).toString();
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
