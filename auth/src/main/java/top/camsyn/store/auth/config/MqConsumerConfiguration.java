package top.camsyn.store.auth.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.auth.constant.MQConstants;
import top.camsyn.store.auth.rocketmq.processor.OneParamActionListener;
import top.camsyn.store.auth.service.impl.AccountService;
import top.camsyn.store.commons.entity.auth.Account;
import top.camsyn.store.commons.exception.RocketMqException;
import top.camsyn.store.commons.factory.MQConsumerFactory;

import javax.annotation.Resource;

/**
 * @author Chen_Kunqiu
 */
@Configuration
public class MqConsumerConfiguration {

    @Resource
    MQConsumerFactory consumerFactory;

    @Resource
    AccountService accountService;

    @Bean(name = "creatAccountConsumer")
    public DefaultMQPushConsumer creatAccountConsumer() throws RocketMqException {

        return consumerFactory.getRunningConsumer(MQConstants.CREATE, MQConstants.ACCOUNT,
                new OneParamActionListener<Account>(accountService::createAccount, Account.class));
    }
}
