package top.camsyn.store.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.review.controller.ReviewLogController;
import top.camsyn.store.review.service.ReviewLogService;


@SpringBootTest
class ReviewApplicationTests {
    @Autowired
    ReviewLogService reviewLogService;

    @Test
    void contextLoads() {
//        reviewLogService.selectByRid("28");
//        reviewLogService.updateByRid("28","0");
//        reviewLogService.report("38","违约",2);
        System.out.println(reviewLogService.selectReportRecord("11910215"));
    }

}
