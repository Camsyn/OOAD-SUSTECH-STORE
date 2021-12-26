package top.camsyn.store.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.client.RestTemplate;
import top.camsyn.store.commons.client.OrderClient;
import top.camsyn.store.commons.entity.request.CartItem;
import top.camsyn.store.commons.entity.request.CartRequest;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.request.controller.CartController;
import top.camsyn.store.request.controller.RequestController;
import top.camsyn.store.request.controller.RpcController;
import top.camsyn.store.request.dto.SearchDto;
import top.camsyn.store.request.service.CartService;
import top.camsyn.store.request.service.RequestService;
import top.camsyn.store.request.test.RequestClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "top.camsyn.store.request.test")
class RequestApplicationTests {
    public static final String USER = "{\"sid\":11911626, \"user_name\":\"11911626\", \"password\":\"123456\",\"email\":\"11911626@mail.susetch.edu.cn\",\"status\":0,\"roles\":[\"admin\",\"root\"]}";
    @Autowired
    CartController cartController;

    @Autowired
    RequestService requestService;

    @Autowired
    RpcController rpcController;

//    static RestTemplate restTemplate = new RestTemplate();

    @Autowired
    OrderClient orderClient;

    @Autowired
    CartService cartService;

//    @Autowired
//    RequestClient requestClient;

    @Test
    void contextLoads() {
        System.out.println(requestService.getById(1));
        System.out.println(requestService.getById(1111));
    }


    @Test
    void testOpenFeign() {
        System.out.println(orderClient.test(0));
    }

    @Test
    void testSearch() {
//        System.out.println(requestService.getById(7));
        SearchDto search = SearchDto.builder().queryStr("te").searchStrategy(0)
                .page(1).limit(3).labels(Arrays.asList("汽车", "test")).build();
        List<Request> result = requestService.search(search);
        System.out.println(result.size());
        System.out.println(JSONArray.toJSONString(result, true));
        System.out.println();
//        search =  SearchDto.builder().searchStrategy(0).page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
//        search =  SearchDto.builder().queryStr("record").page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
//        search =  SearchDto.builder().labels(Collections.singletonList("test")).page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
//        search =  SearchDto.builder().timeState(2).after(LocalDateTime.now()).page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
//        search =  SearchDto.builder().timeState(2).before(LocalDateTime.now()).page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
//        search =  SearchDto.builder().firstOrder("id").isFirstOrderAsc(false).page(1).limit(10).build();
//        result = requestService.search(search);
//        System.out.println(search);
//        System.out.println(result);
//        System.out.println();
    }


    @Test
    void testCart() {
        final List<CartRequest> cart = cartService.getCartList(11910620);
        System.out.println(JSONArray.toJSONString(cart, true));
    }

    @Test
    void testApi() {
        System.out.println(rpcController.getRequest(123124));
    }

    @Test
    void testPushRequest() {

    }

    @Test
    void testPullCart() {
        final CartItem cartItem = cartService.getById(23);
        cartItem.setState(1);
        cartService.updateBatchById(Arrays.asList(cartItem));
    }

    @Test
    void testPullRequest() {
        Result<Object> error = requestService.pullRequest(11, 1, 11910620);
        System.out.println(JSON.toJSONString(error, true));
    }

    public static void main(String[] args) {
        List<String> strings = JSON.parseArray("'[\"asd\",\"qwe\"]'", String.class);
        System.out.println(strings);
    }
}
