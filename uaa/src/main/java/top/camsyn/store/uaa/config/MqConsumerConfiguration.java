package top.camsyn.store.uaa.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.commons.entity.User;
import top.camsyn.store.uaa.constant.VerifyConstant;
import top.camsyn.store.uaa.exception.RocketMqException;
import top.camsyn.store.uaa.factory.MQConsumerFactory;
import top.camsyn.store.uaa.props.MqConsumerProperties;
import top.camsyn.store.uaa.rocketmq.processor.OneParamActionListener;
import top.camsyn.store.uaa.service.impl.AccountService;

import javax.annotation.Resource;

/**
 * @author Chen_Kunqiu
 */
@Configuration
@EnableConfigurationProperties(MqConsumerProperties.class)
public class MqConsumerConfiguration {

    @Resource
    MQConsumerFactory consumerFactory;

    @Resource
    AccountService accountService;

    @Bean(name = "creatAccountConsumer")
    public DefaultMQPushConsumer creatAccountConsumer() throws RocketMqException {
        return consumerFactory.getRunningConsumer(VerifyConstant.VERIFY_TOPIC, VerifyConstant.VERIFY_MAIL_TAGS,
                new OneParamActionListener<User>(accountService::createAccount, User.class));
    }
}
