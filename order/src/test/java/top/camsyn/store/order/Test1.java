package top.camsyn.store.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import top.camsyn.store.order.controller.RpcController;
import top.camsyn.store.order.service.TradeRecordService;

@SpringBootTest
public class Test1 {

    @Autowired
    TradeRecordService service;

    @Autowired
    RpcController controller;

    @Test
    void test(){
        System.out.println(service.getById(38));
        System.out.println(controller.reviewOrder(38).getData());
    }

}
