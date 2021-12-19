package top.camsyn.store.order.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;
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
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.order.mapper.TradeRecordMapper;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

@Service
public class TradeRecordService extends SuperServiceImpl<TradeRecordMapper, TradeRecord> {
    @Autowired
    OrderMailService mailService;

    @Autowired
    UserClient userClient;

    @Autowired
    RequestClient requestClient;

    @Autowired
    RedisLockRegistry lockRegistry;

    public List<TradeRecord> pageOfPullOrders(int sid, Integer page, Integer pageSize) {
        return page(new Page<>(page, pageSize),
                new LambdaQueryWrapper<TradeRecord>()
                        .eq(TradeRecord::getPuller, sid)
                        .orderByDesc(TradeRecord::getCreateTime))
                .getRecords();
    }

    public List<TradeRecord> pageOfPushOrders(int sid, Integer page, Integer pageSize) {
        return page(new Page<>(page, pageSize),
                new LambdaQueryWrapper<TradeRecord>()
                        .eq(TradeRecord::getPusher, sid)
                        .orderByDesc(TradeRecord::getCreateTime))
                .getRecords();
    }


    @SneakyThrows
    public void rollbackUnfinishedOrder(TradeRecord record) {
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
                    rollbackBuy(record);
                    break;
                case RequestConstants.SELL:
                    rollbackSell(record);
                    break;
                default:

                    break;
            }
            request.setSaleCount(request.getSaleCount() - record.getTradeCnt());
            requestClient.updateRequestForRpc(request);
            mailService.sendWhenOrderTerminate(record);
            record.setState(OrderConstants.TERMINATED);
            saveOrUpdate(record);
        } finally {
            LockHelper.unlock(lock);
        }
    }

    private void rollbackSell(TradeRecord record) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                final double totalPrice = record.getSinglePrice() * record.getTradeCnt();

                // 买方先扣钱, 买方等买方确认后再加钱
                final Result<User> result = userClient.changeLiyuan(record.getPuller(), totalPrice);
                checkRpcResult(result, record);

            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {

            }
            break;
            default:
                break;
        }
    }

    private void rollbackBuy(TradeRecord record) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {

            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {

            }
            break;
            default:
                break;
        }
    }


    @SneakyThrows
    public void postHandle(TradeRecord record) {
        final Lock lock = lockRegistry.obtain(record.getRequestId());
        try {
            LockHelper.tryLock(lock);
            switch (record.getType()) {
                case RequestConstants.BUY:
                    postHandleBuy(record);
                    break;
                case RequestConstants.SELL:
                    postHandleSell(record);
                    break;
                default:
                    break;
            }
            mailService.sendWhenOrderFinish(record);
            record.setState(OrderConstants.FINISHED);
            saveOrUpdate(record);
        } finally {
            LockHelper.unlock(lock);
        }
    }


    public void postHandleBuy(TradeRecord record) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                final double totalPrice = record.getSinglePrice() * record.getTradeCnt();
                // 买方先扣钱, 买方等买方确认后再加钱
                final Result<User> result = userClient.changeLiyuan(record.getPuller(), totalPrice);
                checkRpcResult(result, record);
            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {
            }
            break;
            default:
                break;
        }
    }


    public void postHandleSell(TradeRecord record) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                final double totalPrice = record.getSinglePrice() * record.getTradeCnt();
                // 买方先扣钱, 买方等买方确认后再加钱
                final Result<User> result = userClient.changeLiyuan(record.getPusher(), totalPrice);
                checkRpcResult(result, record);
            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {
            }
            break;
            default:
                break;
        }
    }

    @SneakyThrows
    public void preHandle(TradeRecord record) {
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
                    preHandleBuy(record, request);
                    break;
                case RequestConstants.SELL:
                    preHandleSell(record, request);
                    break;
                default:
                    break;
            }
            record.setState(OrderConstants.PUBLISHED);
            save(record);
            mailService.sendWhenOrderGenerate(record);
            request.setSaleCount(request.getSaleCount() + record.getTradeCnt());
            requestClient.updateRequestForRpc(request);
        } finally {
            LockHelper.unlock(lock);
        }

    }

    public void preHandleBuy(TradeRecord record, Request request) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN:
            {
                record.setPullerConfirm(1);
//                record.setState(OrderConstants.PUBLISHED);
//                tradeRecordService.save(record);
            }


            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {
            }
            default:
                break;
        }

    }

    public void preHandleSell(TradeRecord record, Request request) {
        switch (record.getTradeType()) {
            case RequestConstants.LIYUAN: {
                final double totalPrice = record.getSinglePrice() * record.getTradeCnt();

                // 买方先扣钱, 买方等买方确认后再加钱
                final Result<User> result = userClient.changeLiyuan(record.getPuller(), -totalPrice);
                checkRpcResult(result, record);
                record.setPusherConfirm(1);
            }
            break;

            case RequestConstants.PAYCODE:
            case RequestConstants.THIRD_PART:
            case RequestConstants.PRIVATE: {

            }
            break;
            default:
                break;
        }

    }


    public List<TradeRecord> getAvailablePullerUnconfirmed() {
        return baseMapper.getAvailablePullerUnconfirmed();
    }

    public List<TradeRecord> getAvailablePusherUnconfirmed() {
        return baseMapper.getAvailablePusherUnconfirmed();
    }

    @SneakyThrows
    public <T> T checkRpcResult(Result<T> result, TradeRecord record) {
        if (!result.isSuccess()) {
            mailService.sendWhenOrderGenerateFail(record);
            throw new BusinessException("rpc 调用失败");
        }
        return result.getData();
    }

    public boolean checkPermissionForRollback(Integer sid, TradeRecord record){
        switch (record.getType()){
            case RequestConstants.BUY:{
                if (record.getTradeType() == RequestConstants.LIYUAN){
                    return Objects.equals(sid, record.getPuller());
                }
            }

            case RequestConstants.SELL:{
                if (record.getTradeType() == RequestConstants.LIYUAN){
                    return Objects.equals(sid, record.getPusher());

                }
            }
        }
        return false;
    }
}
