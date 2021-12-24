package top.camsyn.store.request.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;
import top.camsyn.store.request.dto.SearchDto;
//import top.camsyn.store.request.mq.source.RequestMqProducerSource;
import top.camsyn.store.request.service.LabelService;
import top.camsyn.store.request.service.RequestMailService;
import top.camsyn.store.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    LabelService labelService;

//    @Autowired
//    RequestMqProducerSource mqProducer;

    @Autowired
    UserClient userClient;

    @Autowired
    RequestMailService mailService;

    @Autowired
    OrderClient orderClient;

    @PostMapping("/push")
    public Result<Request> pushRequest(@RequestBody Request request) {
        log.info("pushRequest");
        final UserDto user = UaaHelper.getCurrentUser();
        request.setPusher(user.getSid()).setState(0).setPusherEmail(user.getEmail());
        requestService.save(request);
        // TODO: 2021/11/22 审核
        // TODO: 邮件提醒
        mailService.sendWhenPush(user.getEmail(), request);
        log.info("已发往审核 user{}", user);
        return Result.succeed(request, "已发往审核");
    }

    @PutMapping("/update")
    public Result<Request> updateRequest(@RequestBody Request request) {
        log.info("updateRequest");
        final UserDto user = UaaHelper.assertAdmin(request.getId());
        Request req = requestService.getById(request.getId());
        if (req == null) {
            log.info("请求不存在 user{}", user);
            return Result.failed("请求不存在");
        }
        if (req.getState() != 3 && req.getState() != 0) {
            log.info("只能修改审核中或关闭中的请求 user{}", user);
            return Result.failed("只能修改审核中或关闭中的请求");
        }

        if (req.getSaleCount() > request.getCount()) {
            log.info("总数量不得小于已售数量 user{}", user);
            return Result.failed("总数量不得小于已售数量");
        }
        req.setState(0);
        requestService.updateById(req);
        // TODO: 2021/11/22 审核
        // TODO: 邮件提醒
        mailService.sendWhenModify(user.getEmail(), request);

        log.info("已发往审核 user{}", user);
        return Result.succeed(request, "已发往审核");
    }

    @PutMapping("/withdraw")
    public Result<Request> withdrawRequest(@RequestParam("requestId") Integer requestId) {
        log.info("withdrawRequest");
        int loginSid = UaaHelper.getLoginSid();
        User user = userClient.getUser(loginSid).getData();
        if (user == null) {
            log.info("查无此用户");
            return Result.failed("查无此用户");
        }
        Request req = requestService.getById(requestId);

        if (req == null) {
            log.info("请求不存在或请求未关闭 user{}", user);
            return Result.failed("请求不存在或请求未关闭");
        }
        if (req.getState() != 3 && req.getState() != 2) {
            log.info("只有open或close的请求有权撤回 user{}", user);
            return Result.failed("只有open或close的请求有权撤回");
        }
        UaaHelper.assertAdmin(req.getPusher());


        req.setState(4);

        if (req.liyuanPayBuyReq()) {
            double returnedLiyuan = req.getExactPrice() * (req.getCount() - req.getSaleCount());
            user.setLiyuan(user.getLiyuan() + returnedLiyuan);
        }

        // TODO: 2021/11/22 一致性， 分布式锁
        userClient.updateUser(user);
        requestService.updateById(req);

        mailService.sendWhenWithdraw(user.getEmail(), req);
        log.info("已成功撤回 user{}", user);
        return Result.succeed(req, "已成功撤回");
    }

    @PutMapping("/close")
    public Result<Request> closeRequest(@RequestParam("requestId") Integer requestId) {
        log.info("开始关闭请求");
        int loginSid = UaaHelper.getLoginSid();
        Request req = requestService.getById(requestId);
        if (req == null || req.getState() != 2) {
            return Result.failed("请求不存在或请求无权关闭");
        }
        UaaHelper.assertAdmin(req.getPusher());

        req.setState(3);
        requestService.updateById(req);
        log.info("关闭请求成功");
        return Result.succeed(req, "已成功关闭请求");
    }


    @PutMapping("/open")
    public Result<Request> openRequest(@RequestParam("requestId") Integer requestId) {
        log.info("开始打开请求");
        Request req = requestService.getById(requestId);
        if (req == null || req.getState() != 2) {
            log.info("请求不存在或无权打开");
            return Result.failed("请求不存在或请求无权打开");
        }
        UaaHelper.assertAdmin(req.getPusher());

        req.setState(2);
        requestService.updateById(req);
        log.info("成功打开请求");
        return Result.succeed(req, "已成功打开请求");
    }


    @GetMapping("/search")
    public Result<List<Request>> search(@RequestBody SearchDto searchDto) {
        log.info("开始搜索");
        final List<Request> search = requestService.search(searchDto);
        log.info("搜索成功");
        return Result.succeed(search, "搜索成功");
    }

    @PutMapping("/pull")
    public Result pullRequest(@RequestParam("requestId") Integer requestId, @RequestParam("count") Integer count) {
        log.info("正在拉取请求");
        int loginSid = UaaHelper.getLoginSid();
        Result<Object> error = requestService.pullRequest(requestId, count, loginSid);
        if (error != null){
            log.info("消费失败");
            return error;
        }
        log.info("成功消费");
        return Result.succeed("已成功下单， 订单生成中");
    }


}
