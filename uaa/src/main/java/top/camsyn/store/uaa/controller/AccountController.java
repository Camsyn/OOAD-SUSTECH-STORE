package top.camsyn.store.uaa.controller;


import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.entity.Account;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.uaa.model.AuthUser;
import top.camsyn.store.uaa.service.impl.AccountService;
import top.camsyn.store.uaa.service.impl.MailService;
import top.camsyn.store.uaa.service.impl.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import top.camsyn.store.uaa.util.VerifyUtils;

import java.security.Principal;


@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @Autowired
    VerifyService verifyService;

    @PostMapping("/login")
    public Result<Account> login(@RequestBody AuthUser user) {
        final Account loginUser = accountService.getLoginUser(user);
        if (loginUser==null){
            return Result.failed("用户id或邮箱尚未注册, 请注册后再登录");
        }
        if (!accountService.comparePassword(user.getPassword(), loginUser.getPassword())) {
            return Result.of(null, CodeEnum.LoginFail.getCode(), "密码错误");
        }
        return Result.of(loginUser, CodeEnum.SUCCESS.getCode(), "成功登录");
    }

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody Account user) {
        if (!VerifyUtils.isSustechEmail(user.getEmail())){
            return Result.failed("非南科大邮箱, 不予注册");
        }
        if (accountService.isSidExist(user.getSid())) {
            return Result.failed("用户已存在, 无法注册");
        }
        final String verifyId = verifyService.generateVerifyId(user);
        mailService.sendVerificationLink(user.getEmail(), verifyId);
        return Result.succeed(true, "成功发送注册请求, 请到验证邮箱");
    }

    @GetMapping("/user")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
