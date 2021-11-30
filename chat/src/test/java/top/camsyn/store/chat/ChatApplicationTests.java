package top.camsyn.store.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import top.camsyn.store.chat.service.ChatRecordService;
import top.camsyn.store.chat.service.ChatService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringBootTest()
//@WebAppConfiguration
class ChatApplicationTests {

    @Autowired
    ChatRecordService chatService;

    @Test
    void contextLoads() {
        System.out.println(chatService.getById(1000)==null);
    }

}
