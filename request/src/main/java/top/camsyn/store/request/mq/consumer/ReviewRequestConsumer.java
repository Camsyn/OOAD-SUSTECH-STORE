package top.camsyn.store.request.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.request.mq.source.RequestMqConsumerSource;

@Component
@Slf4j
public class ReviewRequestConsumer {

    /**
     * 监听配置中的相关 topic的消息
     */
    @Transactional
    @StreamListener(RequestMqConsumerSource.REVIEW_INPUT)
    public void onMessage(@Payload Request request) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), request);

    }

}
