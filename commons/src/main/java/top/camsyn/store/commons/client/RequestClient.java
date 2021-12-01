package top.camsyn.store.commons.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.RequestHystrix;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;

@FeignClient(name = "request", fallback = RequestHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.auth.controller.UserController")
@RequestMapping("/request")
@ResponseBody
public interface RequestClient {



    @PutMapping("/close")
    Result<Request> closeRequest(@RequestParam("requestId") Integer requestId);

    @PutMapping("/open")
    Result<Request> openRequest(@RequestParam("requestId") Integer requestId);




    @GetMapping("/rpc/get")
    Result<Request> getRequest(@RequestParam("requestId") Integer requestId);

    @PutMapping("/rpc/update")
    Result<Request> updateRequest(@RequestBody Request request);

    @PutMapping("/rpc/update/state")
    Result<Request> updateRequestState(@RequestParam("requestId") Integer requestId, @RequestParam("state") Integer state);


    @PutMapping("/rpc/drop")
    Result<Request> dropRequest(@RequestParam("requestId") Integer requestId);


    @PutMapping("/rpc/update")
    Result<Boolean> updateRequestForRpc(@RequestBody Request request);
}
