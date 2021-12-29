package top.camsyn.store.order.service;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.service.MailService;

/**
 * @author Chen_Kunqiu
 */
@Service
public class OrderMailService extends MailService {
    public static final String SIGNATURE = "订单服务中心";

    public void sendWhenOrderFinish(TradeRecord record){
        sendMail(record.getPusherEmail(), SIGNATURE+": 订单完成, 订单号: "+record.getId(), "双方均已确认该订单");
        sendMail(record.getPullerEmail(), SIGNATURE+": 订单完成, 订单号: "+record.getId(), "双方均已确认该订单");
    }
    public void sendWhenOrderGenerate(TradeRecord record){
        sendMail(record.getPusherEmail(), SIGNATURE+": 订单提醒, 订单号: "+record.getId(), "您发起的请求已被成功拉取, 请及时确认");
        sendMail(record.getPullerEmail(), SIGNATURE+": 订单提醒, 订单号: "+record.getId(), "您拉取的请求已成功生成订单, 请及时确认");
    }

    public void sendWhenOrderGenerateFail(TradeRecord record){
        sendMail(record.getPullerEmail(), SIGNATURE+": 订单提醒: 拉取失败", "对应拉取请求不可行");
    }
    public void sendWhenOrderGenerateFail(TradeRecord record, String msg){
        sendMail(record.getPullerEmail(), SIGNATURE+": 订单提醒: 拉取失败", msg);
    }
    public void sendWhenOrderTerminate(TradeRecord record){
        sendMail(record.getPullerEmail(), SIGNATURE+": 订单已被终止", "经审核，此订单已被终止，交易行为将回滚");
    }

    public void sendWhenRollback(String goal, TradeRecord record){
        sendMail(goal, "订单撤回", "对方已撤回订单，请及时确认。订单详情：\n"+ JSON.toJSONString(record, true));
    }

}
