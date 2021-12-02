package top.camsyn.store.review.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.review.mq.source.OrderMqConsumerSource;


@Component
@Slf4j
public class RequestOrderConsumer {

    /**
     * 监听配置中的相关topic的消息, 这里应是对request内容的审核
     */
    @Transactional
    @StreamListener(OrderMqConsumerSource.REQUEST_INPUT)
    public void onMessage(@Payload Request request) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), request);

    }

}
