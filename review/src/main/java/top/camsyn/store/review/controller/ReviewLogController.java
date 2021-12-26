package top.camsyn.store.review.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.entity.review.ReviewLog;
import top.camsyn.store.review.service.ReviewLogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ReviewLogController {

    @Autowired
    private ReviewLogService reviewLogService;

    //管理员
    @GetMapping("/selectAll")
    public List<ReviewLog> selectAll(){
        log.info("查询所有举报记录");
        return reviewLogService.selectAll();
    }

    @GetMapping("/selectPassed")
    public List<ReviewLog> selectPassed(){
        log.info("查询所有审核通过的举报记录");
        return reviewLogService.selectByState("1");
    }

    @GetMapping("/selectDeciding")
    public List<ReviewLog> selectDeciding(){
        log.info("查询所有待审核的举报记录");
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("0"));
        list.addAll(reviewLogService.selectByState("3"));
        return list;
    }

    @GetMapping("/selectFailed")
    public List<ReviewLog> selectFailed(){
        log.info("查询所有审核未通过的举报记录");
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("2"));
        list.addAll(reviewLogService.selectByState("4"));
        return list;
    }

    @GetMapping("/selectByRid")
    public ReviewLog selectByRid(@RequestParam("R_id") String R_id){
        log.info("通过R_id查询举报记录");
        return reviewLogService.selectByRid(R_id);
    }

    @PutMapping("/updateByRid")//0: 审核不通过  1: 审核通过
    public String updateByRid(@RequestParam("R_id") String R_id, @RequestParam("operate") String operate){
        log.info("修改审核记录状态");
        return reviewLogService.updateByRid(R_id, operate);
    }

    @PostMapping("/autoReviewUser")//false: 审核不通过  true: 审核通过
    public String autoReviewUser(@RequestParam("t_id") String t_id){
        log.info("自动审核用户");
        return reviewLogService.autoReview(t_id, 0);
    }

    @PostMapping("/autoReviewRequest")//false: 审核不通过  true: 审核通过
    public String autoReviewRequest(@RequestParam("t_id") String t_id){
        log.info("自动审核请求");
        return reviewLogService.autoReview(t_id, 1);
    }

    @PostMapping("/autoReviewOrder")//false: 审核不通过  true: 审核通过
    public String autoReviewOrder(@RequestParam("t_id") String t_id){
        log.info("自动审核订单");
        return reviewLogService.autoReview(t_id, 2);
    }

    @PostMapping("/autoReviewChat")//false: 审核不通过  true: 审核通过
    public String autoReviewChat(@RequestParam("t_id") String t_id){
        log.info("自动审核聊天记录");
        return reviewLogService.autoReview(t_id, 3);
    }

    @PostMapping("/autoReviewCircle")//false: 审核不通过  true: 审核通过
    public String autoReviewCircle(@RequestParam("t_id") String t_id){
        log.info("自动审核动态");
        return reviewLogService.autoReview(t_id, 4);
    }

    @PostMapping("/autoReviewComment")//false: 审核不通过  true: 审核通过
    public String autoReviewComment(@RequestParam("t_id") String t_id){
        log.info("自动审核评论");
        return reviewLogService.autoReview(t_id, 5);
    }

    @GetMapping("/selectReportsAboutUser")
    public List<ReviewLog> selectReportsAboutUser(@RequestParam("t_id") String t_id){
        log.info("查询某用户的被举报记录");
        return reviewLogService.selectByTC(t_id, "0");
    }

    @GetMapping("/selectReportsAboutRequest")
    public List<ReviewLog> selectReportsAboutRequest(@RequestParam("t_id") String t_id){
        log.info("查询某请求的被举报记录");
        return reviewLogService.selectByTC(t_id, "1");
    }

    @GetMapping("/selectReportsAboutOrder")
    public List<ReviewLog> selectReportsAboutOrder(@RequestParam("t_id") String t_id){
        log.info("查询某订单的被举报记录");
        return reviewLogService.selectByTC(t_id, "2");
    }

    @GetMapping("/selectReportsAboutChat")
    public List<ReviewLog> selectReportsAboutChat(@RequestParam("t_id") String t_id){
        log.info("查询某聊天记录的被举报记录");
        return reviewLogService.selectByTC(t_id, "3");
    }

    @GetMapping("/selectReportsAboutCircle")
    public List<ReviewLog> selectReportsAboutCircle(@RequestParam("t_id") String t_id){
        log.info("查询某动态的被举报记录");
        return reviewLogService.selectByTC(t_id, "4");
    }

    @GetMapping("/selectReportsAboutComment")
    public List<ReviewLog> selectReportsAboutComment(@RequestParam("t_id") String t_id){
        log.info("查询某评论的被举报记录");
        return reviewLogService.selectByTC(t_id, "5");
    }

    @GetMapping("/selectSendingAboutUser")
    public List<ReviewLog> selectSendingAboutUser(@RequestParam("t_id") String t_id){
        log.info("查询某用户的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "0");
    }

    @GetMapping("/selectSendingAboutRequest")
    public List<ReviewLog> selectSendingAboutRequest(@RequestParam("t_id") String t_id){
        log.info("查询某请求的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "1");
    }

    @GetMapping("/selectSendingAboutOrder")
    public List<ReviewLog> selectSendingAboutOrder(@RequestParam("t_id") String t_id){
        log.info("查询某订单的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "2");
    }

    @GetMapping("/selectSendingAboutChat")
    public List<ReviewLog> selectSendingAboutChat(@RequestParam("t_id") String t_id){
        log.info("查询某聊天记录的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "3");
    }

    @GetMapping("/selectSendingAboutCircle")
    public List<ReviewLog> selectSendingAboutCircle(@RequestParam("t_id") String t_id){
        log.info("查询某动态的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "4");
    }

    @GetMapping("/selectSendingAboutComment")
    public List<ReviewLog> selectSendingAboutComment(@RequestParam("t_id") String t_id){
        log.info("查询某评论的自动审核记录");
        return reviewLogService.selectSendingByTC(t_id, "5");
    }

    @GetMapping("/selectReportRecord")
    public List<ReviewLog> selectReportRecord(@RequestParam(name="i_id", required = false, defaultValue = "-1") String i_id){
        log.info("查询某用户的举报记录（默认为当前登录用户）");
        return reviewLogService.selectReportRecord(i_id);
    }

    //用户
    @PostMapping("/reportUser")
    public String reportUser(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        log.info("举报用户 id{}", t_id);
        return reviewLogService.report(t_id, desc, 0);
    }

    @PostMapping("/reportRequest")
    public String reportRequest(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        log.info("举报请求 id{}", t_id);
        return reviewLogService.report(t_id, desc, 1);
    }

    @PostMapping("/reportOrder")
    public String reportOrder(@RequestParam("t_id") String t_id, @RequestParam("desc") String desc){
        log.info("举报订单 id{}", t_id);
        return reviewLogService.report(t_id, desc, 2);
    }

    @PutMapping("/argue")
    public String argue(@RequestParam("R_id") String R_id, @RequestParam("desc") String desc){
        log.info("申诉");
        return reviewLogService.argue(R_id, desc);
    }


}
