package top.camsyn.store.commons.client;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.camsyn.store.commons.client.callback.ChatHystrix;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.model.Result;

@FeignClient(value = "chat", fallback = ChatHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.chat.ChatApplication")
@RequestMapping("/rpc")
public interface ChatClient {

    @GetMapping("/chatRecord/get")
    Result<ChatRecord> getChatRecord(@RequestParam("id") Integer id);

    @GetMapping("/circleMessage/get")
    Result<CircleMessage> getCircleMessage(@RequestParam("id") Integer id);


    /**
     * 若是文件类型的聊天记录， 请审核微服务将对应的文件也一并删除 （涉及file微服务）
     * @param id id
     * @return 成功删除与否
     */
    @DeleteMapping("/chatRecord/delete")
    Result<Boolean> deleteChatRecord(@RequestParam("id") Integer id);
    /**
     * 动态若含有图片链接，请一并删除， 请审核微服务将对应的文件也一并删除 （涉及file微服务）
     * @param id id
     * @return 成功删除与否
     */
    @DeleteMapping("/circleMessage/delete")
    Result<Boolean> deleteCircleMessage(@RequestParam("id") Integer id);
}
