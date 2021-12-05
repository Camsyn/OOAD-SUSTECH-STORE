package top.camsyn.store.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.constant.RequestConstants;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.service.RequestMailService;
import top.camsyn.store.request.service.RequestService;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 专门提供给微服务间rpc调用的
 */
@RestController
@RequestMapping("/rpc")
@Slf4j
public class RpcController {

    @Autowired
    RequestService requestService;

    @Autowired
    RequestMailService mailService;

    /**
     * 审核微服务用来修改request的审核状态
     * @param requestId 待修改的request的id
     * @param state 要修改为的审核状态（详情见 Request类的定义）
     * @return
     */
    @PutMapping("/request/update/state")
    public Result<Request> updateRequestState(@RequestParam("requestId") Integer requestId, @RequestParam("state") Integer state) {
        log.info("审核后处理request");
        Request request = requestService.getById(requestId);
        request.setState(state);
        if (Objects.equals(state, RequestConstants.OPEN)){
            request.setPushTime(LocalDateTime.now());
            mailService.sendWhenSuccessPush(request.getPusherEmail(), request);
        }
        requestService.updateById(request);
        return Result.succeed(request, "成功修改审核状态");
    }


    @PutMapping("/request/drop")
    public Result<Request> dropRequest(@RequestParam("requestId") Integer requestId) {
        return updateRequestState(requestId, 5);
    }

    @GetMapping("/request/get")
    public Result<Request> getRequest(@RequestParam("requestId") Integer requestId) {
        return Result.succeed(requestService.getById(requestId));
    }

    @PutMapping("/request/update")
    public Result<Boolean> updateRequestForRpc(@RequestBody Request request) {
        return Result.succeed(requestService.updateById(request));
    }
}
