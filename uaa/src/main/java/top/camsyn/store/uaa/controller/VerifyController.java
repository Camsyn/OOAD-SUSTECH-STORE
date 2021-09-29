package top.camsyn.store.uaa.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.camsyn.store.commons.entity.Account;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.uaa.constant.VerifyConstant;
import top.camsyn.store.uaa.service.impl.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Chen_Kunqiu
 */
@RestController
@RequestMapping("/verify")
public class VerifyController {

    public static final Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);


    @Autowired
    DefaultMQProducer mqProducer;

    @Autowired
    VerifyService verifyService;

    @GetMapping("/mail")
    public Result<Account> verifyMail(@RequestParam(name = "id") String vId) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        if (verifyService.verifyVidExist(vId)) {
            final Object content = verifyService.getUserByVerifyId(vId);
            if (content instanceof Account) {
                final Account user = (Account) content;
                final String jsonString = JSON.toJSONString(user);
                Message message = new Message(VerifyConstant.VERIFY_TOPIC, VerifyConstant.VERIFY_MAIL_TAGS, jsonString.getBytes());
                mqProducer.send(message);
                LOGGER.info("发送验证消息( topic:{}, tags:{} ): vid:{}, user:{}", VerifyConstant.VERIFY_TOPIC, VerifyConstant.VERIFY_MAIL_TAGS, vId, jsonString);
                verifyService.terminateVerifying(vId);
                return Result.succeed(user, "验证成功, 异步创建账户");
            } else {
                return Result.failed(null, "验证失败, 验证id错误");
            }
        }
        return Result.failed(null, "验证失败, 验证id错误");
    }

    @GetMapping("/sid")
    public Result<Boolean> verifySidExist(@RequestParam(name = "sid") int sid) {
        LOGGER.info("查询sid是否已注册, sid=" + sid);
        return verifyService.verifyUserExist(sid) ?
                Result.failed(false, "用户名已存在") :
                Result.succeed(true, "用户名不存在,可以注册");
    }

    @GetMapping("/email")
    public Result<Boolean> verifySidExist(@RequestParam(name = "email") String email) {
        LOGGER.info("查询sid是否已注册, email=" + email);
        return verifyService.verifyUserExist(email) ?
                Result.failed(false, "用户名已存在") :
                Result.succeed(true, "用户名不存在,可以注册");
    }
}
