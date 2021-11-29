package top.camsyn.store.order.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.order.mq.source.OrderMqConsumerSource;

@Component
@Slf4j
public class RequestOrderConsumer {

    /**
     * 监听配置中的相关 topic的消息
     */
    @Transactional
    @StreamListener(OrderMqConsumerSource.REQUEST_INPUT)
    public void onMessage(@Payload Request request) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), request);

    }

}
