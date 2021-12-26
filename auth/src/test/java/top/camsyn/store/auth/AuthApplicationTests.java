package top.camsyn.store.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.auth.service.impl.UserService;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.entity.user.User;

@SpringBootTest
class AuthApplicationTests {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;



    @Test
    void contextLoads() {
        final User one = userService.getOne(11910215);
        System.out.println(one);
        one.getFollow().add(11910620);
        userService.updateById(one);
//        userService.getOne();
//        accountService.createAccount(Account.builder().sid(1).email("12").password("123").build());
    }

}
