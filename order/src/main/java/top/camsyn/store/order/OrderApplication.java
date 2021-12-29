package top.camsyn.store.order;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import top.camsyn.store.order.mq.source.OrderMqConsumerSource;

@SpringBootApplication
//@EnableBinding({OrderMqConsumerSource.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
