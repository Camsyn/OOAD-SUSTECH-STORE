package top.camsyn.store.auth.controller;


import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.constant.MQConstants;
import top.camsyn.store.auth.model.AuthUser;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.auth.service.impl.VerifyService;
import top.camsyn.store.commons.entity.Account;
import top.camsyn.store.commons.model.CodeEnum;
import top.camsyn.store.commons.model.Result;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    DefaultMQProducer mqProducer;

    @Autowired
    AccountService accountService;



    @Autowired
    VerifyService verifyService;

    @PostMapping("/login")
    public Result<Account> login(@RequestBody AuthUser account) {
        final Account loginUser = accountService.getLoginAccount(account);
        if (loginUser==null){
            return Result.failed("用户id或邮箱尚未注册, 请注册后再登录");
        }
        if (!accountService.comparePassword(account.getPassword(), loginUser.getPassword())) {
            return Result.of(null, CodeEnum.LoginFail.getCode(), "密码错误");
        }
        return Result.of(loginUser, CodeEnum.SUCCESS.getCode(), "成功登录");
    }


    @GetMapping("/create")
    public Result<Account> publishCreateAccountMsg(@RequestParam(name = "id") String vId) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        if (verifyService.isKeyExist(vId)) {
            final Object content = verifyService.getValByKey(vId);
            if (content instanceof Account) {
                final Account user = (Account) content;
                final String jsonString = JSON.toJSONString(user);
                Message message = new Message(MQConstants.CREATE, MQConstants.ACCOUNT, jsonString.getBytes());
                mqProducer.send(message);
                log.info("发送验证消息( topic:{}, tags:{} ): vid:{}, user:{}", MQConstants.CREATE, MQConstants.ACCOUNT, vId, jsonString);
                verifyService.terminateVerifying(vId);
                return Result.succeed(user, "验证成功, 异步创建账户");
            } else {
                return Result.failed(null, "验证失败, 验证id错误");
            }
        }
        return Result.failed(null, "验证失败, 验证id错误");
    }

    @RequestMapping("/modify/password/captcha")
    public Result publishModifyPasswordMsg(@RequestParam(name = "sid") int sid,
                                           @RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        final Account account = accountService.findBySid(sid);
        if (account==null){
            return Result.failed(null,"账户不存在");
        }
        if (accountService.comparePassword(oldPassword,account.getPassword())) {
            return Result.failed(null,"原密码错误");
        }
        if (accountService.modifyPassword(account,newPassword)==null) {
            return Result.failed(null,"服务器错误");
        }
        return Result.succeed("修改密码成功");
    }
    @RequestMapping("/modify/password/oldPassword")
    public Result publishModifyPasswordMsg(@RequestParam(name = "username") String username,
                                           @RequestParam("captcha") String captcha,
                                           @RequestParam("newPassword") String newPassword) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        final Account account = accountService.getLoginAccount(username);
        if (account==null){
            return Result.failed(null,"账户不存在");
        }
        if (!verifyService.isKeyExist(account.getEmail())){
            return Result.failed(false,"未发送验证码至邮箱");
        }
        final String compared = verifyService.getValByKey(account.getEmail()).toString();
        if (!compared.equals(captcha)){
            return Result.failed("验证码错误");
        }
        if (accountService.modifyPassword(account,newPassword)==null) {
            return Result.failed(null,"服务器错误");
        }
        return Result.succeed("修改密码成功");
    }


    @GetMapping("/get")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
