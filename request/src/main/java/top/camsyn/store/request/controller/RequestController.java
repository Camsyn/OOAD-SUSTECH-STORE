package top.camsyn.store.request.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.dto.SearchDto;
import top.camsyn.store.request.service.RequestService;

import java.util.List;

@RestController
@Slf4j

public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping()
    public Result<Request> publishRequest(@RequestBody Request request){
        int loginSid = UaaHelper.getLoginSid();
        request.setPublisher(loginSid).setState(0);
        requestService.save(request);

        return Result.succeed(request,"已发往审核");
    }

    @GetMapping("/search")
    public Result<List<Request>> search(@RequestBody SearchDto searchDto){
        final List<Request> search = requestService.search(searchDto);
        return Result.succeed(search,"搜索成功");
    }

}
