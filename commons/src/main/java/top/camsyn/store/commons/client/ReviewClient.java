package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.ReviewHystrix;
import top.camsyn.store.commons.entity.review.ReviewLog;

import java.util.List;

@FeignClient(value = "review", fallback = ReviewHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.review.controller.ReviewLogContorller")
@ResponseBody
public interface ReviewClient {
    //查询所有审核记录
    @GetMapping("/selectAll")
    List<ReviewLog> selectAll();

    @GetMapping("/selectPassed")
    List<ReviewLog> selectPassed();

    @GetMapping("/selectDeciding")
    List<ReviewLog> selectDeciding();

    @GetMapping("/selectFailed")
    List<ReviewLog> selectFailed();

    @GetMapping("/selectByRid")
    ReviewLog selectByRid(@RequestParam("R_id") String R_id);

    @PutMapping("/updateByRid")//operate:  0: 审核不通过  1: 审核通过
    String updateByRid(@RequestParam("R_id") String R_id, @RequestParam("operate") String operate);

    @PostMapping("/autoReviewUser")
    String autoReviewUser(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewRequest")
    String autoReviewRequest(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewOrder")
    String autoReviewOrder(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewChat")
    String autoReviewChat(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewCircle")
    String autoReviewCircle(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewComment")
    String autoReviewComment(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutUser")
    List<ReviewLog> selectReportsAboutUser(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutRequest")
    List<ReviewLog> selectReportsAboutRequest(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutOrder")
    List<ReviewLog> selectReportsAboutOrder(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutChat")
    List<ReviewLog> selectReportsAboutChat(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutCircle")
    List<ReviewLog> selectReportsAboutCircle(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportsAboutComment")
    List<ReviewLog> selectReportsAboutComment(@RequestParam("t_id") String t_id);

    @GetMapping("/selectReportRecord")
    List<ReviewLog> selectReportRecord(@RequestParam(name="i_id", required = false, defaultValue = "-1") String i_id);

}
