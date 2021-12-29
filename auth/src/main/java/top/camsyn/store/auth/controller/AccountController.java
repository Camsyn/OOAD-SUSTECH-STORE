package top.camsyn.store.auth.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.constant.MQConstants;
import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.auth.service.impl.VerifyService;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    AccountService accountService;



    @Autowired
    VerifyService verifyService;

    @PostMapping("/login")
    public Result<Account> login(@RequestBody AuthUser account) {
        log.info("正在登录 account: {}",account);
        final Account loginUser = accountService.getLoginAccount(account);
        if (loginUser==null){
            log.info("用户id或邮箱尚未注册, 请注册后再登录");
            return Result.failed("用户id或邮箱尚未注册, 请注册后再登录");
        }
        if (!accountService.comparePassword(account.getPassword(), loginUser.getPassword())) {
            log.info("密码错误");
            return Result.of(null, CodeEnum.LoginFail.getCode(), "密码错误");
        }
        log.info("登录成功");
        return Result.of(loginUser, CodeEnum.SUCCESS.getCode(), "成功登录");
    }


    @GetMapping("/create")
    public Result<Account> createAccount(@RequestParam(name = "id") String vId)  {
        log.info("正在创建账户 vId: {}", vId);
        if (verifyService.isKeyExist(vId)) {
            final Object content = verifyService.getValByKey(vId);
            if (content instanceof Account) {
                final Account user = (Account) content;
                verifyService.terminateVerifying(vId);
                accountService.createAccount(user);
                log.info("创建账户: vid:{}, user:{}", vId, user);
                return Result.succeed(user, "验证成功, 创建账户");
            } else {
                log.info("验证失败, 验证id错误");
                return Result.failed(null, "验证失败, 验证id错误");
            }
        }
        log.info("验证失败, 验证id错误");
        return Result.failed(null, "验证失败, 验证id错误");
    }

    @RequestMapping("/modify/password/oldPassword")
    public Result publishModifyPasswordMsg(@RequestParam(name = "sid") int sid,
                                           @RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword)  {
        log.info("开始更改密码 sid: {} oldPassword: {} newPassword: {}", sid, oldPassword, newPassword);
        final Account account = accountService.findBySid(sid);
        if (account==null){
            log.info("账户不存在");
            return Result.failed(null,"账户不存在");
        }
        if (accountService.comparePassword(oldPassword,account.getPassword())) {
            log.info("原密码错误");
            return Result.failed(null,"原密码错误");
        }
        if (accountService.modifyPassword(account,newPassword)==null) {
            log.info("服务器错误");
            return Result.failed(null,"服务器错误");
        }
        log.info("修改密码成功");
        return Result.succeed("修改密码成功");
    }

    @RequestMapping("/modify/password/captcha")
    public Result publishModifyPasswordMsg(@RequestParam(name = "username") String username,
                                           @RequestParam("captcha") String captcha,
                                           @RequestParam("newPassword") String newPassword)  {
        log.info("开始更改密码 username: {} captcha: {} newPassword: {}", username, captcha, newPassword);
        final Account account = accountService.getLoginAccount(username);
        if (account==null){
            log.info("账户不存在");
            return Result.failed(null,"账户不存在");
        }
        if (!verifyService.isKeyExist(account.getEmail())){
            log.info("未发送验证码至邮箱");
            return Result.failed(false,"未发送验证码至邮箱");
        }
        final String compared = verifyService.getValByKey(account.getEmail()).toString();
        if (!compared.equals(captcha)){
            log.info("验证码错误");
            return Result.failed("验证码错误");
        }
        if (accountService.modifyPassword(account,newPassword)==null) {
            log.info("服务器错误");
            return Result.failed(null,"服务器错误");
        }
        log.info("修改密码成功");
        return Result.succeed("修改密码成功");
    }


    @GetMapping("/get")
    public Principal getCurrentUser(Principal principal) {
        log.info("获取当前用户{}", principal);
        return principal;
    }
}
