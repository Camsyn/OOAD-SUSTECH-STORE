package top.camsyn.store.order.mq.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.order.mq.source.OrderMqConsumerSource;
import top.camsyn.store.order.service.TradeRecordService;

@Component
@Slf4j
public class RequestOrderConsumer {

    @Autowired
    TradeRecordService tradeRecordService;



    /**
     * 监听配置中的相关 topic的消息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @StreamListener(OrderMqConsumerSource.REQUEST_INPUT)
    public void onMessage(@Payload TradeRecord record) {
        log.info("处理请求微服务发送来的订单");
        tradeRecordService.preHandle(record);
    }






}
