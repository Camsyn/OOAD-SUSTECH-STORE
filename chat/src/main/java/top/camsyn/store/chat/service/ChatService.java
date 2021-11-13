package top.camsyn.store.chat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.camsyn.store.chat.entity.ChatRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRecordService chatRecordService;


    public List<ChatRecord> queryChatList(int sendSid, int recvSid, int count){
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                    .eq(ChatRecord::getSendId, sendSid).eq(ChatRecord::getRecvId, recvSid)
                        .orderByDesc(ChatRecord::getSendTime).last("limit " + count);
        return chatRecordService.list(queryWrapper);
    }

    public List<ChatRecord> queryChatList(int sendSid, int recvSid, LocalDateTime timeBefore, int count){
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                    .eq(ChatRecord::getSendId, sendSid).eq(ChatRecord::getRecvId, recvSid)
                        .lt(ChatRecord::getSendTime, timeBefore)
                        .orderByDesc(ChatRecord::getSendTime).last("limit " + count);
        return chatRecordService.list(queryWrapper);
    }

    public Map<Integer,  List<ChatRecord>> queryAllLatestChatList(int recvSid,int userSize ,int count){
        QueryWrapper<ChatRecord> senderQuery = new QueryWrapper<ChatRecord>()
                .select("send_id, min(is_read) as is_read, max(recv_time) as recv_time")
                .eq("recv_id",recvSid).groupBy("send_id")
                .orderByAsc("is_read").orderByDesc("recv_time")
                .last("limit "+userSize);
        List<ChatRecord> senders = chatRecordService.list(senderQuery);

        return senders.stream().collect(Collectors.toMap(ChatRecord::getSendId, i -> queryChatList(i.getSendId(), recvSid, count)));
    }

}
