package top.camsyn.store.commons.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.RequestHystrix;
import top.camsyn.store.commons.constant.RequestConstants;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;

import java.time.LocalDateTime;
import java.util.Objects;

@FeignClient(value = "request", fallback = RequestHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.request.controller.RequestController")
@ResponseBody
public interface RequestClient {

    /**
     * 设置请求状态为关闭close
     * @param requestId
     * @return
     */
    @PutMapping("/request/close")
    Result<Request> closeRequest(@RequestParam("requestId") Integer requestId);

    /**
     * 设置请求状态为 open
     * @param requestId
     * @return
     */
    @PutMapping("/request/open")
    Result<Request> openRequest(@RequestParam("requestId") Integer requestId);



    @PutMapping("/rpc/request/update/state")
    Result<Request> updateRequestState(@RequestParam("requestId") Integer requestId, @RequestParam("state") Integer state) ;


    @PutMapping("/rpc/request/drop")
    Result<Request> dropRequest(@RequestParam("requestId") Integer requestId);

    @GetMapping("/rpc/request/get")
    Result<Request> getRequest(@RequestParam("requestId") Integer requestId) ;

    @PutMapping("/rpc/request/update")
    Result<Boolean> updateRequestForRpc(@RequestBody Request request) ;
}
