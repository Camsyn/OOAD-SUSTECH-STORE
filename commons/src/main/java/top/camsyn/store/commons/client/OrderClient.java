package top.camsyn.store.commons.client;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.camsyn.store.commons.client.callback.OrderHystrix;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.model.Result;

@FeignClient(value = "order",fallback = OrderHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.order.controller.OrderController")
@RequestMapping("/rpc")
public interface OrderClient {
    @SneakyThrows
    @PutMapping("/order/terminate")
    Result<TradeRecord> terminateOrder(Integer orderId);

    @SneakyThrows
    @PutMapping("/order/review")
    Result<TradeRecord> reviewOrder(Integer orderId);

    @RequestMapping("/test/{id}")
    String test(@PathVariable("id") int id);
}

