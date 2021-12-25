package top.camsyn.store.order.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.constant.OrderConstants;
import top.camsyn.store.commons.entity.order.Order;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.helper.LockHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.order.service.TradeRecordService;

import java.sql.ResultSet;

@Slf4j
@RestController()
@RequestMapping("/rpc")
public class RpcController {
    @Autowired
    RedisLockRegistry lockRegistry;
    @Autowired
    TradeRecordService recordService;

    @SneakyThrows
    @PutMapping("/order/terminate")
    public Result<TradeRecord> terminateOrder(@RequestParam("orderId") Integer orderId) {
        log.info("订单终结");
        return LockHelper.lockTask(lockRegistry, orderId,
                () -> {
                    TradeRecord order = recordService.getById(orderId);
                    if (order.isFinished()) {
                        throw new BusinessException("订单已完成，无法中断");
                    }
                    recordService.rollbackUnfinishedOrder(order);
                    return Result.succeed(order);
                }
        );
//        Lock lock = lockRegistry.obtain(orderId);
//        try {
//
//            LockHelper.tryLock(lock);
//        }finally {
//            LockHelper.unlock();
//        }

    }

    @SneakyThrows
    @PutMapping("/order/review")
    public Result<TradeRecord> reviewOrder(@RequestParam("orderId") Integer orderId) {
        log.info("订单审核");
        return LockHelper.lockTask(lockRegistry, orderId,
                () -> {
                    TradeRecord order = recordService.getById(orderId);
                    if (order.isFinished()) {
                        throw new BusinessException("订单已完成，无法申诉");
                    }
                    order.setState(OrderConstants.REVIEWING);
                    recordService.updateById(order);
                    return Result.succeed(order);
                }
        );
    }

    @PutMapping("/order/restore")
    Result<TradeRecord> restoreOrder(@RequestParam("orderId") Integer orderId){
        log.info("订单审核通过，无异常");
        return LockHelper.lockTask(lockRegistry, orderId,
                () -> {
                    TradeRecord order = recordService.getById(orderId);
                    if (order.isFinished()) {
                        throw new BusinessException("订单已完成，无法申诉");
                    }
                    order.setState(OrderConstants.PUBLISHED);
                    recordService.updateById(order);
                    return Result.succeed(order);
                }
        );
    }



    @PostMapping("/order/generate")
    public Result<TradeRecord> generateOrder(@RequestBody TradeRecord record){
        log.info("处理请求微服务发送来的订单");
        recordService.preHandle(record);
        log.info("处理完成");
        return Result.succeed(record);
    }


    @RequestMapping("/test/{id}")
    public String test(@PathVariable("id") int id){
        if (id == 0) return "test";
        return recordService.getById(id).toString();
    }
}
