package top.camsyn.store.request.mq.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RequestMqProducerSource {
    /**
     * 给订单微服务发送消息
     */
    @Output("order-output")
    MessageChannel orderOutput();

    @Output("test")
    MessageChannel testOutput();
}
