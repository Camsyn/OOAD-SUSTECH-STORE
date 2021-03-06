package top.camsyn.store.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.chat.service.ChatRecordService;
import top.camsyn.store.chat.service.CircleMessageService;
import top.camsyn.store.chat.service.CommentService;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.model.Result;

@Slf4j
@RestController
@RequestMapping("/rpc")
public class RpcController {
    @Autowired
    ChatRecordService chatRecordService;

    @Autowired
    CircleMessageService circleMessageService;

    @Autowired
    CommentService commentService;

    @GetMapping("/chatRecord/get")
    Result<ChatRecord> getChatRecord(@RequestParam("id") Integer id) {
        log.info("rpc：getChatRecord, id: {}", id);
        return Result.succeed(chatRecordService.getById(id));
    }

    @GetMapping("/circleMessage/get")
    Result<CircleMessage> getCircleMessage(@RequestParam("id") Integer id) {
        log.info("rpc：getCircleMessage, id: {}", id);
        return Result.succeed(circleMessageService.getById(id));

    }


    @DeleteMapping("/chatRecord/delete")
    Result<Boolean> deleteChatRecord(@RequestParam("id") Integer id) {
        log.info("rpc：deleteChatRecord, id: {}", id);
        return Result.succeed(chatRecordService.removeById(id));
    }

    @DeleteMapping("/circleMessage/delete")
    Result<Boolean> deleteCircleMessage(@RequestParam("id") Integer id) {
        log.info("rpc：deleteCircleMessage, id: {}", id);
        return Result.succeed(circleMessageService.removeById(id));
    }


    @GetMapping("/comment/get")
    public Result<Comment> getComment(@RequestParam("id") Integer commentId){
        log.info("getComment, id: {}",commentId);
        return Result.succeed(commentService.getById(commentId));
    }

    @DeleteMapping("/comment/delete")
    public Result<Boolean> deleteComment(@RequestParam("id") Integer commentId){
        log.info("deleteComment, id: {}",commentId);
        return Result.succeed(commentService.removeById(commentId));
    }

}
