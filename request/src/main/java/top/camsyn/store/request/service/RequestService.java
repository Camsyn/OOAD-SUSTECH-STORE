package top.camsyn.store.request.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.commons.entity.order.TradeRecord;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;
import top.camsyn.store.request.dto.SearchDto;
import top.camsyn.store.request.mapper.RequestMapper;

import java.util.List;

@Slf4j
@Service
public class RequestService extends SuperServiceImpl<RequestMapper, Request> {

    @Autowired
    UserClient userClient;
    @Autowired
    OrderClient orderClient;
    @Autowired
    RequestMailService mailService;

    public List<Request> getRequestPageByLabel(String labelName, int page, int pageSize) {
        QueryWrapper<Request> query = new QueryWrapper<Request>()
                .eq("label_name", labelName)
                .orderByDesc("update_time");
        return this.baseMapper.pageOfRequestByLabel(new Page<>(page, pageSize), query);
    }

    public List<Request> getRequestRandomlyByLabel(String labelName, int count) {
        QueryWrapper<Request> query = new QueryWrapper<Request>()
                .eq("label_name", labelName)
                .orderByAsc("rand()").last("limit " + count);
        return this.baseMapper.getRequestByLabel(query);
    }

    public List<Request> getRequestRandomly(int count) {
        return query().orderByAsc("rand()").last("limit " + count).list();
    }

    public List<Request> getRequestPage(int page, int pageSize) {
        return page(new Page<>(page, pageSize)).getRecords();
    }

    public List<Request> search(SearchDto searchDto) {
        final IPage<Request> result = baseMapper.search(new Page<>(searchDto.page, searchDto.limit), searchDto);
        return result.getRecords();
    }


    public Result<Object> pullRequest(Integer requestId, Integer count, int loginSid) {
        User user = userClient.getUser(loginSid).getData();
        if (user == null) {
            log.info("查无此用户");
            return Result.failed("查无此用户");
        }
        Request req = getById(requestId);
        if (req == null || req.getState() != 2) {
            log.info("请求不存在或请求未开放");
            return Result.failed("请求不存在或请求未开放");
        }
        if (req.getPusher().equals(loginSid)) {
            log.info("无法拉取自己发布的请求");
            return Result.failed("无法拉取自己发布的请求");
        }
        int restCnt = req.getCount() - req.getSaleCount();
        if (restCnt < count) {
            log.info("余量不足，请求拉取失败");
            return Result.failed("余量不足，请求拉取失败");
        }
        if (req.liyuanPaySellReq()) {
            double consume = req.getExactPrice() * count;
            Double liyuanBalance = user.getLiyuan();
            if (liyuanBalance < consume) {
                log.info("余额不足，请充值");
                return Result.failed("余额不足，请充值");
            }
        }

        // TODO: 2021/11/22 第三方支付
        // TODO: 2021/11/22 订单微服务
        // TODO: 2021/12/4 幂等操作的实现
        TradeRecord order = TradeRecord.builder().requestId(requestId)
                .puller(loginSid).pullerEmail(user.getEmail())
                .pusher(req.getPusher()).pusherEmail(req.getPusherEmail())
                .type(req.getType()).tradeType(req.getTradeType()).category(req.getCategory())
                .state(0).tradeCnt(count).singlePrice(req.getExactPrice()).build();
        log.info("发送订单生成请求至消息队列，order: {}", order);
        System.out.println(orderClient.generateOrder(order));
//        mqProducer.orderOutput().send(MessageBuilder.withPayload(order).build());
        mailService.sendWhenPull(user.getEmail(), req);
        return null;
    }
}
