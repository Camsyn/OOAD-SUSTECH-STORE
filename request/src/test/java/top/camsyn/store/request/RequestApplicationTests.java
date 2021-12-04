package top.camsyn.store.request;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.camsyn.store.commons.entity.request.Request;
import top.camsyn.store.request.dto.SearchDto;
import top.camsyn.store.request.service.RequestService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class RequestApplicationTests {
    @Autowired
    RequestService requestService;
    @Test
    void contextLoads() {
        System.out.println(requestService.getById(1));
        System.out.println(requestService.getById(1111));
    }

    @Test
    void testSearch(){
        SearchDto search = SearchDto.builder().queryStr("te").searchStrategy(1).page(1).limit(10).build();
        List<Request> result = requestService.search(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().searchStrategy(0).page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().queryStr("record").page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().labels(Collections.singletonList("test")).page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().timeState(2).after(LocalDateTime.now()).page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().timeState(2).before(LocalDateTime.now()).page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
        search =  SearchDto.builder().firstOrder("id").isFirstOrderAsc(false).page(1).limit(10).build();
        result = requestService.search(search);
        System.out.println(search);
        System.out.println(result);
        System.out.println();
    }

    public static void main(String[] args) {
        List<String> strings = JSON.parseArray("'[\"asd\",\"qwe\"]'", String.class);
        System.out.println(strings);
    }
}
