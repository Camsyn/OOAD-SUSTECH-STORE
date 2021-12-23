package top.camsyn.store.request.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.RequestHystrix;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.dto.SearchDto;

import java.util.List;


@FeignClient(value = "request", fallback = RequestHystrix.class)
@ResponseBody
public interface RequestClient {
    @PostMapping("/push")
    Result<Request> pushRequest(@RequestBody Request request);

    @PutMapping("/update")
    Result<Request> updateRequest(@RequestBody Request request);

    @PutMapping("/withdraw")
    Result<Request> withdrawRequest(@RequestParam("requestId") Integer requestId);

    @PutMapping("/close")
    Result<Request> closeRequest(@RequestParam("requestId") Integer requestId);


    @PutMapping("/open")
    Result<Request> openRequest(@RequestParam("requestId") Integer requestId);


    @GetMapping("/search")
    Result<List<Request>> search(@RequestBody SearchDto searchDto);

    @PutMapping("/pull")
    Result pullRequest(@RequestParam("requestId") Integer requestId, @RequestParam("count") Integer count);
}
