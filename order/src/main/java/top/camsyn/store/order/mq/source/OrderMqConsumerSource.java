package top.camsyn.store.order.mq.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderMqConsumerSource {
    String REQUEST_INPUT = "requet_input";

    @Input(REQUEST_INPUT)
    SubscribableChannel requestInput();
}
