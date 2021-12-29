package top.camsyn.store.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.client.UserClient;
import top.camsyn.store.review.service.ReviewLogService;


@SpringBootTest
class ReviewApplicationTests {
    @Autowired
    ReviewLogService reviewLogService;

    @Autowired
    UserClient userClient;

    @Autowired
    OrderClient orderClient;

    @Test
    void contextLoads() {
//        System.out.println(reviewLogService.selectByRid("28"));
//        System.out.println(orderClient.reviewOrder(38).getData());
        System.out.println(reviewLogService.updateByRid("28","0"));
//        System.out.println(userClient.getUser(11910215));
    }

}
