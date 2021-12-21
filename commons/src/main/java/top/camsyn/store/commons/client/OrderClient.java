package top.camsyn.store.commons.client;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.OrderHystrix;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.model.Result;

@FeignClient(value = "order", fallback = OrderHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.order.controller.OrderController")
@RequestMapping("/rpc")
public interface OrderClient {
    @SneakyThrows
    @PutMapping("/order/terminate")
    Result<TradeRecord> terminateOrder(@RequestParam("orderId") Integer orderId);

    @SneakyThrows
    @PutMapping("/order/review")
    Result<TradeRecord> reviewOrder(@RequestParam("orderId") Integer orderId);

    @PostMapping("/order/generate")
    Result<TradeRecord> generateOrder(@RequestBody TradeRecord record);

    @RequestMapping("/test/{id}")
    String test(@PathVariable("id") int id);
}

