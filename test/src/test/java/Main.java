import com.alibaba.fastjson.JSON;
import com.example.test.entity.request.Request;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final Request req = Request.builder()
                .category(1).count(1).description("this is a test")
                .exactPrice(1000.0).labels(Arrays.asList("test", "feign"))
                .title("Feign Test").originalPrice(2000.0)
                .build();
        System.out.println(JSON.toJSONString(req, true));
    }
}
