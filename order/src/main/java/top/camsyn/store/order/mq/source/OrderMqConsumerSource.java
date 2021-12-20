package top.camsyn.store.order.mq.source;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface OrderMqConsumerSource {
    String REQUEST_INPUT = "request-input";
    String TEST = "test";

    @Input(REQUEST_INPUT)
    SubscribableChannel requestInput();

    @Input(TEST)
    SubscribableChannel testInput();


}
