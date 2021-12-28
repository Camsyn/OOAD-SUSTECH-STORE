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

    @PutMapping("/request")
    public Result<Request> modifyLabelsForRequest(@RequestBody Request newRequest) {
        log.info("修改请求的label标签 newRequest: {}", newRequest);
        Integer id = newRequest.getId();
        Request req = requestService.getById(id);
        UaaHelper.assertAdmin(req.getPusher());
        final List<String> oldLabels = req.getLabels();
        final List<String> newLabels = newRequest.getLabels();

//        //并集
//        Collection<String> union = CollectionUtils.union(a, b);
//        //交集
//        Collection<String> intersection = CollectionUtils.intersection(a, b);
//        //交集的补集
//        Collection<String> disjunction = CollectionUtils.disjunction(a, b);
//        //集合相减
//        Collection<String> subtract = CollectionUtils.subtract(a, b);

        final Collection<String> subtract = CollectionUtils.subtract(oldLabels, newLabels);
        final Collection<String> append = CollectionUtils.subtract(newLabels, oldLabels);
        req.setLabels(newLabels);
        requestService.updateById(req);
        labelService.updatePushFrequency(subtract, false);
        labelService.updatePushFrequency(append, true);

        log.info("成功修改请求的label标签");
        return Result.succeed(req.setLabels(newLabels), "成功");
    }


    /**
     * 按频率排序分页返回labels
     */
    @GetMapping("/frequency")
    public Result<List<Label>> getLabelByFrequency(@RequestParam("page") Integer page,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam("isPush") Boolean isPush){
        log.info("请求label频率 page: {}, pageSize: {}", page,pageSize);
        List<Label> labels = labelService.getLabelsByFreqOrder(new Page<>(page, pageSize), isPush);
        log.info("成功请求label频率");
        return Result.succeed(labels,"成功");
    }
}
