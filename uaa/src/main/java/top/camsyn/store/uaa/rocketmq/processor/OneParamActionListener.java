package top.camsyn.store.uaa.rocketmq.processor;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

public class OneParamActionListener <T> implements MessageListenerConcurrently {
    private static final Logger logger = LoggerFactory.getLogger(OneParamActionListener.class);
    private final Consumer<T> action;
    private final Class<T> entityClz;

    public OneParamActionListener(Consumer<T> action, Class<T> entityClz) {
        this.action = action;
        this.entityClz = entityClz;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        if (CollectionUtils.isEmpty(msgs)) {
            logger.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgs.get(0);
        logger.info("接受到的消息为：" + messageExt.toString());
        final String jsonObject = new String(messageExt.getBody());
        if ("test".equals(jsonObject)){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        try{
            //TODO 处理对应的业务逻辑
            final T entity = JSON.parseObject(jsonObject,entityClz);
            action.accept(entity);
            logger.info("处理消息, obj:{}", jsonObject);
        } catch (Exception e){
            logger.error("处理消息时发生异常:"+jsonObject,e);
            //TODO 判断该消息是否重复消费（RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重）
            //TODO 获取该消息重试次数
            if (messageExt.getReconsumeTimes() == 3) {//消息已经重试了3次，如果不需要再次消费，则返回成功 (不然会陷入异常死循环中)
                logger.info("重试3次, 不再继续接收此消息");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
