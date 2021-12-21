package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.camsyn.store.commons.client.callback.ReviewHystrix;
import top.camsyn.store.commons.entity.review.ReviewLog;

import java.util.List;

@FeignClient(value = "review", fallback = ReviewHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.review.controller.ReviewLogContorller")
@ResponseBody
public interface ReviewClient {
    @RequestMapping("/selectAll")
    List<ReviewLog> selectAll();

    @RequestMapping("/selectPassed")
    List<ReviewLog> selectPassed();

    @RequestMapping("/selectDeciding")
    List<ReviewLog> selectDeciding();

    @RequestMapping("/selectFailed")
    List<ReviewLog> selectFailed();

    @PostMapping("/selectByRid")
    ReviewLog selectByRid(@RequestParam("R_id") String R_id);

    @PostMapping("/updateByRid")//operate:  0: 审核不通过  1: 审核通过
    String updateByRid(@RequestParam("R_id") String R_id, @RequestParam("operate") String operate);

    @PostMapping("/autoReviewUser")//false: 审核不通过  true: 审核通过
    boolean autoReviewUser(@RequestParam("t_id") String t_id);

    @PostMapping("/autoReviewRequest")//false: 审核不通过  true: 审核通过
    boolean autoReviewRequest(@RequestParam("t_id") String t_id);

    @PostMapping("/selectReportsAboutUser")
    List<ReviewLog> selectReportsAboutUser(@RequestParam("t_id") String t_id);

    @PostMapping("/selectReportsAboutRequest")
    List<ReviewLog> selectReportsAboutRequest(@RequestParam("t_id") String t_id);

    @PostMapping("/selectReportsAboutOrder")
    List<ReviewLog> selectReportsAboutOrder(@RequestParam("t_id") String t_id);

    @PostMapping("/selectReportRecord")
    List<ReviewLog> selectReportRecord(@RequestParam(name="i_id", required = false, defaultValue = "-1") String i_id);

}
