package top.camsyn.store.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.gateway.props.OAuthProperties;

@SpringBootTest
class GatewayApplicationTests {

    @Autowired
    OAuthProperties authProperties;

    @Test
    void contextLoads() {
        System.out.println(authProperties);
    }

}
