package top.camsyn.store.request.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.service.RequestService;

/**
 * 专门提供给微服务间rpc调用的
 */
@RestController
@RequestMapping("/rpc")
@Slf4j
public class RpcController {

    @Autowired
    RequestService requestService;

    /**
     * 审核微服务用来修改request的审核状态
     * @param requestId 待修改的request的id
     * @param state 要修改为的审核状态（详情见 Request类的定义）
     * @return
     */
    @PutMapping("/reivew/result")
    public Result reviewResult(@RequestParam("requestId") Integer requestId, @RequestParam("state") Integer state){
        log.info("审核后处理request");
        Request request = requestService.getById(requestId);
        request.setState(state);
        requestService.updateById(request);

        return Result.succeed(request,"成功修改审核状态");
    }
}
