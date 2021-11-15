package top.camsyn.store.chat;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;
import top.camsyn.store.chat.entity.Comment;
import top.camsyn.store.chat.service.CircleMessageService;
import top.camsyn.store.chat.service.CommentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
class ChatApplicationTests {
    @Autowired
    CommentService commentService;
    @Autowired
    CircleMessageService circleMessageService;
    @Autowired
    ApplicationContext context;
    @Test
    void contextLoads() {

//        Comment comment = Comment.builder().cmId(1).content("hello").type(0).build();
//        System.out.println(comment);
//        commentService.save(comment);
//        System.out.println(comment);
//        commentService.removeById(comment.getId());
        System.out.println(commentService.getById(3));
    }

}
