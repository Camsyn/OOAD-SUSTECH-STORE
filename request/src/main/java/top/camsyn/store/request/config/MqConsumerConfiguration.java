package top.camsyn.store.request.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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



    @Bean(name = "creatAccountConsumer")
    public DefaultMQPushConsumer creatAccountConsumer() throws RocketMqException {
        // TODO: 2021/11/22 修改
        return consumerFactory.getSemiPreparedConsumer();
//
//        return consumerFactory.getRunningConsumer(MQConstants.CREATE, MQConstants.ACCOUNT,
//                new OneParamActionListener<Account>(accountService::createAccount, Account.class));
    }
}
