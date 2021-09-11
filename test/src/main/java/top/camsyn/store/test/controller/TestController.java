package top.camsyn.store.test.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {
    @Autowired
    DefaultMQProducer mqProducer;

    @RequestMapping("/")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }

    @RequestMapping("/send")
    @PreAuthorize("hasAnyRole('ROLE_selector')")
    public SendResult send() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        return mqProducer.send(new Message("verify", "mail", "test".getBytes()));
    }

    @RequestMapping("/login")
    public String login(){
        return "custom-login";
    }

//    @GetMapping("/getUserInfoList")
//    @PostFilter("filterObject.userName == '小强'")
//    public List<UserInfo> getUserInfoList(){
//        List<UserInfo> list = new ArrayList<>();
//        list.add(new UserInfo("小明","123"));
//        list.add(new UserInfo("小强","123"));
//        list.add(new UserInfo("小红","123"));
//        return list;
//    }


}
