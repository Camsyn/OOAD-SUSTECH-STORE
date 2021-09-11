import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import top.camsyn.store.commons.entity.User;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class Test {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("camsyn-client"));
    }
}
