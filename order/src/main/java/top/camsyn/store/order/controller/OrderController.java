package top.camsyn.store.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.exception.NotSelfException;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;
import top.camsyn.store.order.service.TradeRecordService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    TradeRecordService tradeRecordService;

    @GetMapping("/pull/get")
    public Result<List<TradeRecord>> getPullRecords(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        final UserDto user = UaaHelper.getCurrentUser();
        final List<TradeRecord> orders = tradeRecordService.pageOfPullOrders(user.getSid(), page, pageSize);
        return Result.succeed(orders);
    }

    @GetMapping("/push/get")
    public Result<List<TradeRecord>> getPushRecords(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        final UserDto user = UaaHelper.getCurrentUser();
        final List<TradeRecord> orders = tradeRecordService.pageOfPushOrders(user.getSid(), page, pageSize);
        return Result.succeed(orders);
    }

    @PutMapping("/pull/ensure")
    public Result<TradeRecord> ensurePullOrder(@RequestParam("orderId") Integer orderId) {
        final TradeRecord order = tradeRecordService.getById(orderId);
        final UserDto currentUser = UaaHelper.getCurrentUser();
        if (!order.getPuller().equals(currentUser.getSid()))
            throw new NotSelfException("非本人的请求, 不可操作");
        order.setPullerConfirm(1);
        if (order.isFinished()) {
            tradeRecordService.postHandle(order);
        }
        return Result.succeed(order);
    }


    @PutMapping("/push/ensure")
    public Result<TradeRecord> ensurePushOrder(@RequestParam("orderId") Integer orderId) {
        final TradeRecord order = tradeRecordService.getById(orderId);
        final UserDto currentUser = UaaHelper.getCurrentUser();
        if (!order.getPusher().equals(currentUser.getSid()))
            throw new NotSelfException("非本人的请求, 不可操作");
        order.setPusherConfirm(1);
        if (order.isFinished()) {
            tradeRecordService.postHandle(order);
        }
        return Result.succeed(order);
    }


}
