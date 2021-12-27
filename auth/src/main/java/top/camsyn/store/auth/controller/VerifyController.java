package top.camsyn.store.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.auth.service.impl.AuthMailService;
import top.camsyn.store.auth.service.impl.VerifyService;
import top.camsyn.store.auth.util.VerifyUtils;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.model.Result;


/**
 * @author Chen_Kunqiu
 */
@Slf4j
@RestController
@RequestMapping("/verify")
public class VerifyController {

    public static final Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);

    @Autowired
    AuthMailService mailService;

    @Autowired
    AccountService accountService;


    @Autowired
    VerifyService verifyService;

    @RequestMapping("/modify/password")
    public Result<Boolean> modifyPassword(@RequestParam("username") String username) {
        log.info("开始修改密码 {}",username);
        final Account loginAccount = accountService.getLoginAccount(username);
        if (loginAccount == null) {
            log.info("账户不存在");
            return Result.failed("账户不存在");
        }
        final String email = loginAccount.getEmail();
        if (verifyService.isKeyExist(email)){
            log.info("请勿重复发送邮件(6小时后可重试)");
            return Result.failed(false,"请勿重复发送邮件(6小时后可重试)");
        }
        final String captcha = verifyService.generateCaptcha(email);
        if (!mailService.sendCaptcha(email, captcha)) {
            verifyService.terminateVerifying(email);
            log.info("邮箱发送失败");
            return Result.failed(false,"邮箱发送失败");
        }
        log.info("验证码已发送到邮箱, 请验收 (6小时后可重试)");
        return Result.succeed(true, "验证码已发送到邮箱, 请验收 (6小时后可重试)");
    }

    @GetMapping("/exist/sid")
    public Result<Boolean> verifySidExist(@RequestParam(name = "sid") int sid) {
        LOGGER.info("查询sid是否已注册, sid=" + sid);
        return verifyService.verifyUserExist(sid) ?
                Result.failed(false, "sid已存在") :
                Result.succeed(true, "sid不存在,可以注册");
    }

    @GetMapping("/exist/email")
    public Result<Boolean> verifySidExist(@RequestParam(name = "email") String email) {
        LOGGER.info("查询邮箱是否已注册, email=" + email);
        return verifyService.verifyUserExist(email) ?
                Result.failed(false, "邮箱已存在") :
                Result.succeed(true, "邮箱不存在,可以注册");
    }

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody Account account) {
        log.info("开始注册 {}", account);
        if (!VerifyUtils.isSustechEmail(account.getEmail())){
            log.info("非南科大邮箱, 不予注册");
            return Result.failed("非南科大邮箱, 不予注册");
        }
        if (accountService.isSidExist(account.getSid())) {
            log.info("用户已存在, 无法注册");
            return Result.failed("用户已存在, 无法注册");
        }
        final String verifyId = verifyService.generateVerifyId(account);
        if (!mailService.sendVerificationLink(account.getEmail(), verifyId)) {
            verifyService.terminateVerifying(verifyId);
            log.info("邮箱发送失败");
            return Result.failed(false,"邮箱发送失败");
        }
        log.info("成功发送注册请求，请到验证邮箱");
        return Result.succeed(true, "成功发送注册请求, 请到验证邮箱");
    }
}
