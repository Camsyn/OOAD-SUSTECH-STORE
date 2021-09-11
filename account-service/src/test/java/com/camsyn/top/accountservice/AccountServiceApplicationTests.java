package com.camsyn.top.accountservice;

import com.camsyn.top.accountservice.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountServiceApplicationTests {
    @Autowired
    MailService mailService;

    @Test
    void contextLoads() {
//        mailService.sendMail("11911626@mail.sustech.edu.cn","Hello World!");
    }

}
