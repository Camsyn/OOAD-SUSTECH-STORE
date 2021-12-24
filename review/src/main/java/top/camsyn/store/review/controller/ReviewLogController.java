package top.camsyn.store.review.controller;

import lombok.extern.slf4j.Slf4j;
import top.camsyn.store.commons.entity.review.ReviewLog;
import top.camsyn.store.review.service.ReviewLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ReviewLogController {

    @Autowired
    private ReviewLogService reviewLogService;

    //管理员
    @RequestMapping("/selectAll")
    public List<ReviewLog> selectAll(){
        log.info("查询所有举报记录");
        return reviewLogService.selectAll();
    }

    @RequestMapping("/selectPassed")
    public List<ReviewLog> selectPassed(){
        log.info("查询所有审核通过的举报记录");
        return reviewLogService.selectByState("1");
    }

    @RequestMapping("/selectDeciding")
    public List<ReviewLog> selectDeciding(){
        log.info("查询所有待审核的举报记录");
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("0"));
        list.addAll(reviewLogService.selectByState("3"));
        return list;
    }

    @RequestMapping("/selectFailed")
    public List<ReviewLog> selectFailed(){
        log.info("查询所有审核未通过的举报记录");
        List<ReviewLog> list = new ArrayList<>();
        list.addAll(reviewLogService.selectByState("2"));
        list.addAll(reviewLogService.selectByState("4"));
        return list;
    }

    @PostMapping("/selectByRid")
    public ReviewLog selectByRid(@RequestParam("R_id") String R_id){
        log.info("通过R_id查询举报记录");
        return reviewLogService.selectByRid(R_id);
    }

    @PostMapping("/updateByRid")//0: 审核不通过  1: 审核通过
    public String updateByRid(@RequestParam("R_id") String R_id, @RequestParam("operate") String operate){
        log.info("修改审核记录状态");
        return reviewLogService.updateByRid(R_id, operate);
    }

    @PostMapping("/autoReviewUser")//false: 审核不通过  true: 审核通过
    public boolean autoReviewUser(@RequestParam("t_id") String t_id){
        log.info("自动审核用户");
        return reviewLogService.isPassed(Integer.parseInt(t_id), 0);
    }

    @PostMapping("/autoReviewRequest")//false: 审核不通过  true: 审核通过
    public boolean autoReviewRequest(@RequestParam("t_id") String t_id){
        log.info("自动审核请求");
        return reviewLogService.isPassed(Integer.parseInt(t_id), 1);
    }

    @PostMapping("/selectReportsAboutUser")
    public List<ReviewLog> selectReportsAboutUser(@RequestParam("t_id") String t_id){
        log.info("查询某用户的被举报记录");
        return reviewLogService.selectByTC(t_id, "0");
    }

    @PostMapping("/selectReportsAboutRequest")
    public List<ReviewLog> selectReportsAboutRequest(@RequestParam("t_id") String t_id){
        log.info("查询某请求的被举报记录");
        return reviewLogService.selectByTC(t_id, "1");
    }

    @PostMapping("/selectReportsAboutOrder")
    public List<ReviewLog> selectReportsAboutOrder(@RequestParam("t_id") String t_id){
        log.info("查询某订单的被举报记录");
        return reviewLogService.selectByTC(t_id, "2");
    }

    @PostMapping("/selectReportRecord")
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

    @PostMapping("/argue")
    public String argue(@RequestParam("R_id") String R_id, @RequestParam("desc") String desc){
        log.info("申诉");
        return reviewLogService.argue(R_id, desc);
    }


}
