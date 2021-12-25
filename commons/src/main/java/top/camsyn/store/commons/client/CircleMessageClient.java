package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.CircleMessageHystrix;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.model.Result;


@FeignClient(value = "chat", fallback = CircleMessageHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.review.controller.CircleMessageContorller")
@ResponseBody
public interface CircleMessageClient {
    @GetMapping("/get")
    Result<CircleMessage> getCircleMessage(Integer cmId);

    @DeleteMapping("/delete")
    Result<CircleMessage> deleteCircleMessage(@RequestParam("id") Integer id);

    @GetMapping("")
    Result<Comment> getComment(Integer id);

    @DeleteMapping("/comment/delete")
    Result<Comment> deleteComment(@RequestParam("id") Integer id);
}
