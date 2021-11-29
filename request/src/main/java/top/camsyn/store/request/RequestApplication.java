package top.camsyn.store.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import top.camsyn.store.request.mq.source.RequestMqConsumerSource;
import top.camsyn.store.request.mq.source.RequestMqProducerSource;

@SpringBootApplication
@EnableBinding({RequestMqConsumerSource.class, RequestMqProducerSource.class})
public class RequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestApplication.class, args);
    }

}
