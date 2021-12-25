package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.ChatHystrix;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.model.Result;


@FeignClient(value = "chat", fallback = ChatHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.review.controller.ChatContorller")
@ResponseBody
public interface ChatClient {
    @GetMapping("")
    Result<ChatRecord> getChatRecord(@RequestParam("id") Integer id);

    @DeleteMapping("")
    Result<ChatRecord> deleteChatRecord(@RequestParam("id") Integer id);
}
