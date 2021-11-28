package top.camsyn.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.commons.props.CommonsProperties;

@SpringBootTest(classes = CommonsProperties.class)
class AuthApplicationTests {

    @Autowired
    CommonsProperties commonsProperties;


    @Test
    void contextLoads() {
        System.out.println(commonsProperties.getGatewayIp());
    }
}
