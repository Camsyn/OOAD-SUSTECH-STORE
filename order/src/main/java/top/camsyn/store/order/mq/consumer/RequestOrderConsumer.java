package top.camsyn.store.order.mq.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.camsyn.store.commons.client.RequestClient;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.constant.OrderConstants;
import top.camsyn.store.commons.constant.RequestConstants;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.helper.LockHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.order.mq.source.OrderMqConsumerSource;
import top.camsyn.store.order.service.OrderMailService;
import top.camsyn.store.order.service.TradeRecordService;

import java.util.concurrent.locks.Lock;

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
