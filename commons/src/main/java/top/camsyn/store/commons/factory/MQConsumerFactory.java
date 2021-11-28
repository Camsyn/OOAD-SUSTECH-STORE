package top.camsyn.store.commons.factory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.camsyn.store.commons.enumeration.RocketMqErrorEnum;
import top.camsyn.store.commons.exception.RocketMqException;
import top.camsyn.store.commons.props.MqConsumerProperties;


/**
 * @author Chen_Kunqiu
 */
@Component
@EnableConfigurationProperties(MqConsumerProperties.class)
public class MQConsumerFactory {

    @Autowired
    private MqConsumerProperties consumerProperties;

    public static final Logger LOGGER = LoggerFactory.getLogger(MQConsumerFactory.class);

    public DefaultMQPushConsumer getSemiPreparedConsumer() throws RocketMqException {
        if (StringUtils.isEmpty(consumerProperties.getGroupName())) {
            throw new RocketMqException(RocketMqErrorEnum.PARAMM_NULL, "groupName is null !!!", false);
        }
        if (StringUtils.isEmpty(consumerProperties.getNamesrvAddr())) {
            throw new RocketMqException(RocketMqErrorEnum.PARAMM_NULL, "namesrvAddr is null !!!", false);
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerProperties.getGroupName());
        consumer.setNamesrvAddr(consumerProperties.getNamesrvAddr());
        final Integer consumeThreadMin = consumerProperties.getConsumeThreadMin();
        if (consumeThreadMin != null) {
            consumer.setConsumeThreadMin(consumeThreadMin);
        }
        final Integer consumeThreadMax = consumerProperties.getConsumeThreadMax();
        if (consumeThreadMax != null) {
            consumer.setConsumeThreadMax(consumeThreadMax);
        }
//        consumer.registerMessageListener(mqMessageListenerProcessor);

        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /*
         * 设置消费模型，集群还是广播，默认为集群
         */
        //consumer.setMessageModel(MessageModel.CLUSTERING);
        final Integer consumeMessageBatchMaxSize = consumerProperties.getConsumeMessageBatchMaxSize();
        if (consumeMessageBatchMaxSize != null) {
            consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        }
        return consumer;
    }

    public DefaultMQPushConsumer getRunningConsumer(String topic, String tags, MessageListenerConcurrently callBack) throws RocketMqException {
        final DefaultMQPushConsumer consumer = getSemiPreparedConsumer();
        consumer.registerMessageListener(callBack);
        try {
            /*    *
             * 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*；如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
             */
            consumer.subscribe(topic, tags);
            consumer.start();
            LOGGER.info("consumer is start !!! groupName:{},topics:{},namesrvAddr:{}, topic:{}, tags:{}",
                    consumerProperties.getGroupName(), topic, consumerProperties.getNamesrvAddr(),topic,tags);
        } catch (MQClientException e) {
            LOGGER.error("consumer is break !!! groupName:{},topics:{},namesrvAddr:{}",
                    consumerProperties.getGroupName(), topic, consumerProperties.getNamesrvAddr(), e);
            throw new RocketMqException(e);
        }
        return consumer;
    }
}
