package top.camsyn.store.request.service;

import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.service.MailService;

@Service
public class RequestMailService extends MailService {
    public static final String SIGNATURE = "请求服务中心";
    public void sendWhenPush(String to, Request request){
        sendMail(to, "请求推送确认", String.format("成功推送请求, 请等待审核通过。 请求序列号：%s", request.getId()));
    }
    public void sendWhenSuccessPush(String to, Request request){
        sendMail(to, "请求通过审核", String.format("成功推送请求, 审核已通过审核并成功发布。 请求序列号：%s", request.getId()));
    }

    public void sendWhenModify(String to,Request request){
        sendMail(to, "请求修改确认", String.format("修改请求, 请等待审核通过。 请求序列号：%s", request.getId()));
    }
    public void sendWhenWithdraw(String to,Request request){
        sendMail(to, "请求撤回确认", String.format("撤回请求。 请求序列号：%s", request.getId()));
    }
    public void sendWhenPull(String to, Request request){
        sendMail(to, "请求拉取确认", String.format("成功拉取请求, 请等待订单完成。 请求序列号：%s", request.getId()));
    }
}
