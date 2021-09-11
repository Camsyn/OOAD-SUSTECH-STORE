package top.camsyn.store.uaa.controller;


import top.camsyn.store.commons.entity.User;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.uaa.model.AuthUser;
import top.camsyn.store.uaa.service.impl.AccountService;
import top.camsyn.store.uaa.service.impl.MailService;
import top.camsyn.store.uaa.service.impl.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.uaa.util.VerifyUtils;

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
    public Result<User> login(@RequestBody AuthUser user) {
        final User loginUser = accountService.getLoginUser(user);
        if (loginUser==null){
            return Result.failed("用户id或邮箱尚未注册, 请注册后再登录");
        }
        if (!accountService.comparePassword(user.getPassword(), loginUser.getPassword())) {
            return Result.of(null, CodeEnum.PwdError.getCode(), "密码错误");
        }
        return Result.of(loginUser, CodeEnum.SUCCESS.getCode(), "成功登录");
    }

    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody User user) {
        if (!VerifyUtils.isSustechEmail(user.getEmail())){
            return Result.failed("非南科大邮箱, 不予注册");
        }
        if (accountService.findBySid(user.getSid())!=null) {
            return Result.failed("用户已存在, 无法注册");
        }
        final String verifyId = verifyService.generateVerifyId(user);
        mailService.sendVerificationLink(user.getEmail(), verifyId);
        return Result.succeed(true, "成功发送注册请求, 请到验证邮箱");
    }


}
