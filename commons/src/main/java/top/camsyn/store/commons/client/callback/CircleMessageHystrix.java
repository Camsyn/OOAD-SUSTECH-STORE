package top.camsyn.store.commons.client.callback;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.camsyn.store.commons.client.ChatClient;
import top.camsyn.store.commons.client.CircleMessageClient;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.entity.chat.Comment;
import top.camsyn.store.commons.model.Result;


public class CircleMessageHystrix implements CircleMessageClient {

    @Override
    public Result<CircleMessage> getCircleMessage(Integer cmId){return null;}

    @Override
    public Result<CircleMessage> deleteCircleMessage(Integer id){return null;}

    @Override
    public Result<Comment> getComment(Integer id){return null;}

    @Override
    public Result<Comment> deleteComment(Integer id){return null;}

}
