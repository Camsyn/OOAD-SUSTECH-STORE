package top.camsyn.store.commons.client.callback;

import top.camsyn.store.commons.client.ChatClient;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.model.Result;

public class ChatHystrix implements ChatClient {
    @Override
    public Result<ChatRecord> getChatRecord(Integer id) {
        return null;
    }

    @Override
    public Result<CircleMessage> getCircleMessage(Integer id) {
        return null;
    }

    @Override
    public Result<Boolean> deleteChatRecord(Integer id) {
        return null;
    }

    @Override
    public Result<Boolean> deleteCircleMessage(Integer id) {
        return null;
    }
}