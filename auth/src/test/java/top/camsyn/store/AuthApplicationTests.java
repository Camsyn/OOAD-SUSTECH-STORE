package top.camsyn.store.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.auth.service.impl.UserService;
import top.camsyn.store.commons.entity.auth.Account;

@SpringBootTest
class AuthApplicationTests {

    @Autowired
    AccountService accountService;


    @Test
    void contextLoads() {
        accountService.createAccount(Account.builder().sid(1).email("12").password("123").build());
    }

}
