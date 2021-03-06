package top.camsyn.store.chat.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import top.camsyn.store.chat.mapper.CircleMessageMapper;
import top.camsyn.store.commons.entity.chat.CircleMessage;
import top.camsyn.store.commons.service.impl.SuperServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CircleMessageService extends SuperServiceImpl<CircleMessageMapper, CircleMessage> {
    public List<CircleMessage> getMessageBySid(int sid, int count){
        return lambdaQuery().eq(CircleMessage::getSendId, sid).orderByDesc(CircleMessage::getSendTime)
                .last("limit " + count).list();
    }
    public List<CircleMessage> getMessageBySid(int sid, int count, LocalDateTime before){
        return lambdaQuery().eq(CircleMessage::getSendId, sid)
                .lt(CircleMessage::getSendTime, before)
                .orderByDesc(CircleMessage::getSendTime)
                .last("limit " + count).list();
    }
    public List<CircleMessage> getMessagePageBySid(int sid, int page, int limit){
        return lambdaQuery().eq(CircleMessage::getSendId, sid)
                .orderByDesc(CircleMessage::getSendTime)
                .page(new Page<>(page,limit)).getRecords();
    }
    public List<CircleMessage> getLatestMessage(int count){
        return lambdaQuery().orderByDesc(CircleMessage::getSendTime)
                .last("limit " + count).list();
    }
    public List<CircleMessage> getLatestMessage(int count, LocalDateTime before){
        return lambdaQuery()
                .lt(CircleMessage::getSendTime, before)
                .orderByDesc(CircleMessage::getSendTime)
                .last("limit " + count).list();
    }
    public List<CircleMessage> getLatestMessagePage(int page, int limit){
        return lambdaQuery()
                .orderByDesc(CircleMessage::getSendTime)
                .page(new Page<>(page,limit)).getRecords();
    }
}
