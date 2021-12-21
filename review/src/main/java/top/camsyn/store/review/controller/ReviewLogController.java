package top.camsyn.store.review.controller;

import top.camsyn.store.commons.entity.review.ReviewLog;
import top.camsyn.store.review.service.ReviewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewLogController {

    @Autowired
    private ReviewLogService reviewLogService;

    //管理员
    @RequestMapping("/selectAll")
    public List<ReviewLog> selectAll(){
        return reviewLogService.selectAll();
    }

    @RequestMapping("/selectPassed")
    public List<ReviewLog> selectPassed(){
        return reviewLogService.selectByState("1");
    }

    @RequestMapping("/selectDeciding")
    public List<ReviewLog> selectDeciding(){
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("0"));
        list.addAll(reviewLogService.selectByState("3"));
        return list;
    }

    @RequestMapping("/selectFailed")
    public List<ReviewLog> selectFailed(){
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("2"));
        list.addAll(reviewLogService.selectByState("4"));
        return list;
    }

    @PostMapping("/selectByRid")
    public ReviewLog selectByRid(@RequestParam("R_id") String R_id){
        return reviewLogService.selectByRid(R_id);
    }

    @PostMapping("/updateByRid")//0: 审核不通过  1: 审核通过
    public String updateByRid(@RequestParam("R_id") String R_id, @RequestParam("operate") String operate){
        return reviewLogService.updateByRid(R_id, operate);
    }

    @PostMapping("/autoReviewUser")//false: 审核不通过  true: 审核通过
    public boolean autoReviewUser(@RequestParam("t_id") String t_id){
        return reviewLogService.isPassed(Integer.parseInt(t_id), 0);
    }

    @PostMapping("/autoReviewRequest")//false: 审核不通过  true: 审核通过
    public boolean autoReviewRequest(@RequestParam("t_id") String t_id){
        return reviewLogService.isPassed(Integer.parseInt(t_id), 1);
    }

    @PostMapping("/selectReportsAboutUser")
    public List<ReviewLog> selectReportsAboutUser(@RequestParam("t_id") String t_id){
        return reviewLogService.selectByTC(t_id, "0");
    }

    @PostMapping("/selectReportsAboutRequest")
    public List<ReviewLog> selectReportsAboutRequest(@RequestParam("t_id") String t_id){
        return reviewLogService.selectByTC(t_id, "1");
    }

    @PostMapping("/selectReportsAboutOrder")
    public List<ReviewLog> selectReportsAboutOrder(@RequestParam("t_id") String t_id){
        return reviewLogService.selectByTC(t_id, "2");
    }

    @PostMapping("/selectReportRecord")
    public List<ReviewLog> selectReportRecord(@RequestParam(name="i_id", required = false, defaultValue = "-1") String i_id){
        return reviewLogService.selectReportRecord(i_id);
    }

    //用户
    @PostMapping("/reportUser")
    public String reportUser(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        return reviewLogService.report(t_id, desc, 0);
    }

    @PostMapping("/reportRequest")
    public String reportRequest(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        return reviewLogService.report(t_id, desc, 1);
    }

    @PostMapping("/reportOrder")
    public String reportOrder(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        return reviewLogService.report(t_id, desc, 2);
    }

    @PostMapping("/argue")
    public String argue(@RequestParam("R_id") String R_id, @RequestParam("desc") String desc){
        return reviewLogService.argue(R_id, desc);
    }


}
