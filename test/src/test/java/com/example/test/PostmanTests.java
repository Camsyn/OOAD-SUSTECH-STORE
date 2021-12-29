//package com.example.test;
//
////import com.example.test.entity.request.Request;
////import com.example.test.feign.RequestFeign;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//
//@SpringBootTest
//public class PostmanTests {
//    @Autowired
//    RequestFeign requestClient;
//
//    @Test
//    public void testPushRequest(){
//        final Request req = Request.builder()
//                .category(1).count(1).description("this is a test")
//                .exactPrice(1000.0).labels(Arrays.asList("test", "feign"))
//                .title("Feign Test").originalPrice(2000.0)
//                .build();
//        System.out.println(requestClient.pushRequest(req));
//    }
//
//}
