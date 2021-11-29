package top.camsyn.store.request.mq.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RequestMqConsumerSource {
    String REVIEW_INPUT = "review_input";


    /**
     * 接收请求微服务的的消息
     */
    @Input(REVIEW_INPUT)
    SubscribableChannel reviewInput();
}