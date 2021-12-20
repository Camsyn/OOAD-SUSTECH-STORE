package top.camsyn.store.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.request.mq.source.RequestMqProducerSource;

@RestController
@Slf4j
public class TestController {

    @Autowired
    RequestMqProducerSource source;

    @RequestMapping("/test")
    public String test() {
        log.info("testing message queue...");
        TradeRecord order = TradeRecord.builder().requestId(0)
                .puller(11911626).pullerEmail("camsyn@foxmail.com")
                .pusher(11910620).pusherEmail("camsyn@foxmail.com")
                .type(0).tradeType(0).category(1)
                .state(0).tradeCnt(2).singlePrice(2.0).build();
        source.testOutput().send(MessageBuilder.withPayload(order).build());
        return "testing message queue...";
    }
}
