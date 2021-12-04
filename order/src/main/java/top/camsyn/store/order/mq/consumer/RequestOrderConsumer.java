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

    @Autowired
    OrderMailService mailService;

    @Autowired
    UserClient userClient;

    @Autowired
    RequestClient requestClient;

    @Autowired
    RedisLockRegistry lockRegistry;

    /**
     * 监听配置中的相关 topic的消息
     */
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    @StreamListener(OrderMqConsumerSource.REQUEST_INPUT)
    public void onMessage(@Payload TradeRecord record) {
        log.info("处理请求微服务发送来的订单");
        final Lock lock = lockRegistry.obtain(record.getRequestId());
        try {
            LockHelper.tryLock(lock);
            final Request request = checkRpcResult(requestClient.getRequest(record.getRequestId()), record);
            if (request.getCount() - request.getSaleCount() < record.getTradeCnt()) {
                mailService.sendWhenOrderGenerateFail(record, "目标请求的余量不足");
                throw new BusinessException("目标请求的余量不足");
            }
            switch (record.getType()) {
                case RequestConstants.BUY:
                    handleBuy(record, request);
                    break;
                case RequestConstants.SELL:
                    handleSell(record, request);
                    break;
                default:
                    break;
            }
        } finally {
            LockHelper.unlock(lock);
        }


    }

    public void handleBuy(TradeRecord record, Request request) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                record.setState(OrderConstants.PUBLISHED);
                tradeRecordService.save(record);
                mailService.sendWhenOrderGenerate(record);
            }

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE:
            default:
                break;
        }
        request.setSaleCount(request.getSaleCount() + record.getTradeCnt());
        requestClient.updateRequestForRpc(request);
    }

    public void handleSell(TradeRecord record, Request request) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                final double totalPrice = record.getSinglePrice() * record.getTradeCnt();
                final Result<User> result = userClient.changeLiyuan(record.getPusher(), record.getPuller(), totalPrice);
                checkRpcResult(result, record);
                record.setState(OrderConstants.PUBLISHED);
                tradeRecordService.save(record);
                mailService.sendWhenOrderGenerate(record);
            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE:
            default:
                break;
        }
        request.setSaleCount(request.getSaleCount() + record.getTradeCnt());
        requestClient.updateRequestForRpc(request);
    }

    @SneakyThrows
    public <T> T checkRpcResult(Result<T> result, TradeRecord record) {
        if (!result.isSuccess()) {
            mailService.sendWhenOrderGenerateFail(record);
            throw new BusinessException("rpc 调用失败");
        }
        return result.getData();
    }


}
