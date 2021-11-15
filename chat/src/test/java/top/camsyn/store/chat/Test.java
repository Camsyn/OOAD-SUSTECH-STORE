package top.camsyn.store.chat;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import top.camsyn.store.chat.entity.ChatState;
import top.camsyn.store.chat.entity.Comment;
import top.camsyn.store.chat.service.CommentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        CommentService commentService = new CommentService();
        Comment comment = Comment.builder().cmId(1).content("hello").type(0).build();
        commentService.save(comment);
        System.out.println(comment);

    }
}
