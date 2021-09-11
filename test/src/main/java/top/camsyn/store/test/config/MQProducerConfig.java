package top.camsyn.store.test.config;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQProducerConfig {
    @Bean
    public DefaultMQProducer mqProducer() throws MQClientException {
        DefaultMQProducer producer = new
                DefaultMQProducer("group1");
        // Specify name server addresses.
        producer.setNamesrvAddr("localhost:9876");
        producer.setVipChannelEnabled(false);
        //Launch the instance.
        producer.start();
        return producer;
    }

}
