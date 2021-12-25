package top.camsyn.store.commons.client.callback;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.camsyn.store.commons.client.ChatClient;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.model.Result;


public class ChatHystrix implements ChatClient {

    @Override
    public Result<ChatRecord> getChatRecord(Integer id){return null;}

    @Override
    public Result<ChatRecord> deleteChatRecord(Integer id){return null;}

}
