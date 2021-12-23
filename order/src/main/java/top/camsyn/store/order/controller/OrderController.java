package top.camsyn.store.order.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.constant.OrderConstants;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.exception.NotSelfException;
import top.camsyn.store.commons.helper.LockHelper;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;
import top.camsyn.store.order.service.TradeRecordService;

import java.util.List;
import java.util.concurrent.locks.Lock;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    RedisLockRegistry lockRegistry;
    @Autowired
    TradeRecordService tradeRecordService;

    @GetMapping("/pull/get")
    public Result<List<TradeRecord>> getPullRecords(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        log.info("getPullRecords");
        final UserDto user = UaaHelper.getCurrentUser();
        final List<TradeRecord> orders = tradeRecordService.pageOfPullOrders(user.getSid(), page, pageSize);
        log.info("getPullRecords success");
        return Result.succeed(orders);
    }

    @GetMapping("/push/get")
    public Result<List<TradeRecord>> getPushRecords(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        log.info("getPushRecords");
        final UserDto user = UaaHelper.getCurrentUser();
        final List<TradeRecord> orders = tradeRecordService.pageOfPushOrders(user.getSid(), page, pageSize);
        log.info("getPushRecords success");
        return Result.succeed(orders);
    }

    @SneakyThrows
    @PutMapping("/pull/confirm")
    public Result<TradeRecord> confirmPullOrder(@RequestParam("orderId") Integer orderId) {
        log.info("ensurePullOrder");
        Lock lock = lockRegistry.obtain(orderId);
        try {
            LockHelper.tryLock(lock);
            final TradeRecord order = tradeRecordService.getById(orderId);
            if (order.getState() == OrderConstants.REVIEWING || order.getState() == OrderConstants.TERMINATED){
                throw new BusinessException("此订单已被申诉或冻结，暂时无法确认");
            }
            final UserDto currentUser = UaaHelper.getCurrentUser();
            if (!order.getPuller().equals(currentUser.getSid()))
                throw new NotSelfException("非本人的请求, 不可操作");
            order.setPullerConfirm(1);
            if (order.isFinished()) {
                tradeRecordService.postHandle(order);
            }
            log.info("ensurePullOrder success");
            return Result.succeed(order);
        }finally {
            LockHelper.unlock(lock);

        }

    }


    @SneakyThrows
    @PutMapping("/push/confirm")
    public Result<TradeRecord> confirmPushOrder(@RequestParam("orderId") Integer orderId) {
        log.info("ensurePushOrder");
        Lock lock = lockRegistry.obtain(orderId);
        try {
            LockHelper.tryLock(lock);
            final TradeRecord order = tradeRecordService.getById(orderId);
            if (order.getState() == OrderConstants.REVIEWING || order.getState() == OrderConstants.TERMINATED){
                throw new BusinessException("此订单已被申诉或冻结，暂时无法确认");
            }
            final UserDto currentUser = UaaHelper.getCurrentUser();
            if (!order.getPusher().equals(currentUser.getSid()))
                throw new NotSelfException("非本人的请求, 不可操作");
            order.setPusherConfirm(1);
            if (order.isFinished()) {
                tradeRecordService.postHandle(order);
            }
            log.info("ensurePushOrder success");
            return Result.succeed(order);
        } finally {
            LockHelper.unlock(lock);
        }
    }

    /**
     * 只有卖请求中的pusher 或 买请求中的puller拥有 主动回滚订单的权力
     */
    @PutMapping("/rollback")
    public Result<TradeRecord> rollbackOrder(@RequestParam("orderId") Integer orderId){
        log.info("rollbackOrder");
        UserDto currentUser = UaaHelper.getCurrentUser();
        return LockHelper.lockTask(lockRegistry, orderId,
                () -> {
                    final TradeRecord order = tradeRecordService.getById(orderId);
                    if (!tradeRecordService.checkPermissionForRollback(currentUser.getSid(), order)){
                        throw new BusinessException("没有权限撤回");
                    }
                    if (order.isFinished()) {
                        throw new BusinessException("订单已完成，无法中断");
                    }
                    tradeRecordService.rollbackUnfinishedOrder(order);
                    log.info("rollbackOrder success");
                    return Result.succeed(order);
                });
    }

}
