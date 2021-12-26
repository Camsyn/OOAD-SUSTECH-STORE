package top.camsyn.store.commons.client.callback;

import org.springframework.web.bind.annotation.RequestParam;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.model.Result;

public class OrderHystrix implements OrderClient {
    @Override
    public Result<TradeRecord> terminateOrder(Integer orderId) {
        return null;
    }

    @Override
    public Result<TradeRecord> reviewOrder(Integer orderId) {
        return null;
    }

    @Override
    public Result<TradeRecord> restoreOrder(Integer orderId) {
        return null;
    }

    @Override
    public Result<TradeRecord> generateOrder(TradeRecord record) {
        return null;
    }



    @Override
    public String test(int id) {
        return null;
    }
}
