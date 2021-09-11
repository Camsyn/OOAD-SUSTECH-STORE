package com.camsyn.top.accountservice.controller;

import com.camsyn.top.accountservice.entity.User;
import com.camsyn.top.accountservice.service.AccountService;
import com.camsyn.top.accountservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @Autowired
    ApplicationContext applicationContext;

    @Async
    @PostMapping
    public void register(@RequestBody User user, Model model){
        final String verifyId = UUID.randomUUID().toString();

        mailService.sendVerificationLink(user.getMail(),verifyId);
    }

    @PostMapping
    public void createAccount(@RequestBody User user){
        accountService.createAccount(user);
    }
}
