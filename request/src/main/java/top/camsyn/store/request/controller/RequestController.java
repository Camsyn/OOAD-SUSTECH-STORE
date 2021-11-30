package top.camsyn.store.request.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.entity.order.Order;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.dto.SearchDto;
import top.camsyn.store.request.mq.source.RequestMqProducerSource;
import top.camsyn.store.request.service.LabelService;
import top.camsyn.store.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j
public class RequestController {

    @Autowired
    RequestService requestService;
    @Autowired
    LabelService labelService;

    @Autowired
    RequestMqProducerSource mqProducer;

    @Autowired
    UserClient userClient;

    @PostMapping("/push")
    public Result<Request> pushRequest(@RequestBody Request request){
        int loginSid = UaaHelper.getLoginSid();
        request.setPublisher(loginSid).setState(0);
        requestService.save(request);
        // TODO: 2021/11/22 审核
        return Result.succeed(request,"已发往审核");
    }

    @PutMapping("/update")
    public Result<Request> updateRequest(@RequestBody Request request){
        int loginSid = UaaHelper.getLoginSid();
        Request req = requestService.getById(request.getId());
        if (req == null){
            return Result.failed("请求不存在");
        }
        if (req.getState() != 3 && req.getState()!=0){
            return Result.failed("只能修改审核中或关闭中的请求");
        }
        if (req.getPublisher()!=loginSid) {
            return Result.failed("非发布者，无权修改");
        }
        if (req.getSaleCount() > request.getCount()) {
            return Result.failed("总数量不得小于已售数量");
        }
        req.setState(0);
        requestService.updateById(req);
        // TODO: 2021/11/22 审核
        // TODO: 邮件提醒
        return Result.succeed(request,"已发往审核");
    }

    @PutMapping("/withdraw")
    public Result<Request> withdrawRequest(@RequestParam("requestId") Integer requestId){
        int loginSid = UaaHelper.getLoginSid();
        User user = userClient.getUser(loginSid).getData();
        if (user == null){
            return Result.failed("查无此用户");
        }
        Request req = requestService.getById(requestId);

        if (req == null ){
            return Result.failed("请求不存在或请求未关闭");
        }
        if (req.getState() != 3 && req.getState()!=2){
            return Result.failed("只有open或close的请求有权撤回");
        }
        if (req.getPublisher()!=loginSid) {
            return Result.failed("非发布者，无权修改");
        }

        req.setState(4);

        if (req.isLiyuanPayBuyReq()){
            double returnedLiyuan = req.getExactPrice() * (req.getCount()-req.getSaleCount());
            user.setLiyuan(user.getLiyuan()+returnedLiyuan);
        }

        // TODO: 2021/11/22 一致性， 分布式锁
        userClient.updateUser(user);
        requestService.updateById(req);


        return Result.succeed(req,"已成功撤回");
    }

    @PutMapping("/close")
    public Result<Request> closeRequest(@RequestParam("requestId") Integer requestId){
        int loginSid = UaaHelper.getLoginSid();
        Request req = requestService.getById(requestId);
        if (req == null || req.getState() != 2){
            return Result.failed("请求不存在或请求无权关闭");
        }
        if (req.getPublisher()!=loginSid) {
            return Result.failed("非发布者，无权修改");
        }

        req.setState(3);
        requestService.updateById(req);

        return Result.succeed(req,"已成功关闭请求");
    }


    @GetMapping("/search")
    public Result<List<Request>> search(@RequestBody SearchDto searchDto){
        final List<Request> search = requestService.search(searchDto);
        return Result.succeed(search,"搜索成功");
    }

    @PutMapping("/pull")
    public Result pullRequest(@RequestParam("requestId") Integer requestId, @RequestParam("count") Integer count){
        int loginSid = UaaHelper.getLoginSid();
        User user = userClient.getUser(loginSid).getData();
        if (user == null){
            return Result.failed("查无此用户");
        }
        Request req = requestService.getById(requestId);
        if (req == null || req.getState() != 2){
            return Result.failed("请求不存在或请求未开放");
        }
        int restCnt = req.getCount() - req.getSaleCount();
        if (restCnt < count){
            return Result.failed("余量不足，请求拉取失败");
        }
        if (req.isLiyuanPaySellReq()){
            double consume = req.getExactPrice() * count;
            Double liyuanBalance = user.getLiyuan();
            if (liyuanBalance < consume){
                return Result.failed("余额不足，请充值");
            }
        }
        // TODO: 2021/11/22 第三方支付
        // TODO: 2021/11/22 订单微服务
        Order order = Order.builder().requestId(requestId).puller(loginSid).pusher(req.getPublisher()).state(0).count(count).build();
        log.info("发送订单生成请求至消息队列，order: {}", order);
        mqProducer.orderOutput().send(MessageBuilder.withPayload(order).build());
        return Result.succeed("已成功下单， 订单生成中");
    }


}
