package top.camsyn.store.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import top.camsyn.store.review.mq.source.OrderMqConsumerSource;



@SpringBootApplication
@EnableFeignClients
@EnableBinding({OrderMqConsumerSource.class})

public class ReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewApplication.class, args);
    }

}
