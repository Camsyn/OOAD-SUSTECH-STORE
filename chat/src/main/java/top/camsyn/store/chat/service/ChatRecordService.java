package top.camsyn.store.chat.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import top.camsyn.store.chat.mapper.ChatRecordMapper;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

import java.util.List;

@Service
public class ChatRecordService extends SuperServiceImpl<ChatRecordMapper, ChatRecord> {
    List<Integer> getRelevantChatUsers(Integer sid) {
        return baseMapper.getRelevantChatUser(sid);
    }

}
