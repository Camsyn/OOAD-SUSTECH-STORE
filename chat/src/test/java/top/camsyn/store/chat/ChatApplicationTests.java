package top.camsyn.store.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import top.camsyn.store.chat.service.ChatRecordService;
import top.camsyn.store.chat.service.ChatService;
import top.camsyn.store.commons.entity.chat.ChatRecord;


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


    @Test
    void testDatabase(){
        ChatRecord chat = ChatRecord.builder().recvId(11911626).sendId(11910620).content("hello, this is another test").type(0).build();
        chatService.save(chat);
    }
}
