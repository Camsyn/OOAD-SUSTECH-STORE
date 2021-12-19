package top.camsyn.store.order.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.constant.OrderConstants;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.exception.BusinessException;
import top.camsyn.store.commons.helper.LockHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.order.service.TradeRecordService;

@RestController()
@RequestMapping("/rpc")
public class RpcController {
    @Autowired
    RedisLockRegistry lockRegistry;
    @Autowired
    TradeRecordService recordService;

    @SneakyThrows
    @PutMapping("/order/terminate")
    public Result<TradeRecord> terminateOrder(Integer orderId) {
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
    public Result<TradeRecord> reviewOrder(Integer orderId) {
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

    @RequestMapping("/test/{id}")
    public String test(@PathVariable("id") int id){
        if (id == 0) return "test";
        return recordService.getById(id).toString();
    }
}
