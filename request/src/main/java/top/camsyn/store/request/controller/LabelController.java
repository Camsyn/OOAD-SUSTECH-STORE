package top.camsyn.store.request.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.entity.request.Label;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.service.LabelService;
import top.camsyn.store.request.service.RelationService;
import top.camsyn.store.request.service.RequestService;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/label")
public class LabelController {

    @Autowired
    RequestService requestService;

    @Autowired
    LabelService labelService;

    @Autowired
    RelationService relationService;

    @PutMapping("/request")
    public Result<Request> modifyLabelsForRequest(@RequestBody Request newRequest) {
        log.info("修改请求的label标签");
        Integer id = newRequest.getId();
        Request oldRequest = requestService.getById(id);
        UaaHelper.assertAdmin(oldRequest.getPusher());
        List<String> oldLabels = oldRequest.getLabels();
        List<String> newLabels = newRequest.getLabels();
        Collection<String> labels2append = CollectionUtils.subtract(newLabels, oldLabels);
        Collection<String> labels2delete = CollectionUtils.subtract(oldLabels, newLabels);
        List<Label> deletes = labelService.queryOrCreate(labels2delete);
        relationService.unbindRequestAndLabel(deletes, newRequest);
        List<Label> appends = labelService.queryOrCreate(labels2append);
        relationService.bindRequestAndLabel(appends, newRequest);
        log.info("成功修改请求的label标签");
        return Result.succeed(oldRequest.setLabels(newLabels), "成功");
    }


    /**
     * 按频率排序分页返回labels
     */
    @GetMapping("/frequency")
    public Result<List<Label>> getLabelFrequency(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize){
        log.info("请求label频率");
        List<Label> labels = labelService.getLabelsByFreqOrder(new Page<>(page, pageSize));
        log.info("成功请求label频率");
        return Result.succeed(labels,"成功");
    }
}
