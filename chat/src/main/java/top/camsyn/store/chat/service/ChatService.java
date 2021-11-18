package top.camsyn.store.chat.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.camsyn.store.commons.entity.chat.ChatRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    private ChatRecordService chatRecordService;



    public ChatRecord queryOneChat(int sid1, int sid2,int skip) {
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                        .and(wq -> wq
                                .eq(ChatRecord::getSendId, sid1).eq(ChatRecord::getRecvId, sid2)
                                .or()
                                .eq(ChatRecord::getSendId, sid2).eq(ChatRecord::getRecvId, sid1))
                        .orderByDesc(ChatRecord::getSendTime).last("limit "+skip+", 1");
        return chatRecordService.getOne(queryWrapper);
    }


    public IPage<ChatRecord> queryChatPage(int sid1, int sid2, int page, int pageSize) {
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                        .and(wq -> wq
                                .eq(ChatRecord::getSendId, sid1).eq(ChatRecord::getRecvId, sid2)
                                .or()
                                .eq(ChatRecord::getSendId, sid2).eq(ChatRecord::getRecvId, sid1))
                        .orderByDesc(ChatRecord::getSendTime);
        return chatRecordService.page(new Page<ChatRecord>(page, pageSize), queryWrapper);
    }


    public List<ChatRecord> queryChatList(int sid1, int sid2, int count) {
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                        .and(wq -> wq
                                .eq(ChatRecord::getSendId, sid1).eq(ChatRecord::getRecvId, sid2)
                                .or()
                                .eq(ChatRecord::getSendId, sid2).eq(ChatRecord::getRecvId, sid1))
                        .orderByDesc(ChatRecord::getSendTime).last("limit " + count);
        return chatRecordService.list(queryWrapper);
    }

    public List<ChatRecord> queryChatList(int sid1, int sid2, LocalDateTime timeBefore, int count) {
        LambdaQueryWrapper<ChatRecord> queryWrapper =
                new LambdaQueryWrapper<ChatRecord>()
                        .and(wq -> wq
                                .eq(ChatRecord::getSendId, sid1).eq(ChatRecord::getRecvId, sid2)
                                .or()
                                .eq(ChatRecord::getSendId, sid2).eq(ChatRecord::getRecvId, sid1))
                        .lt(ChatRecord::getSendTime, timeBefore)
                        .orderByDesc(ChatRecord::getSendTime).last("limit " + count);
        return chatRecordService.list(queryWrapper);
    }

    public Map<Integer, List<ChatRecord>> queryAllLatestChatList(int sid, int pageSize, int page, int count) {
        /**
         * select * from test where 1 in (test.a, test.b)
         *     and
         *      test.c =
         *          (select max(t.c) from test t where test.a = t.a and test.b = t.b or test.a = t.b and test.b = t.a)
         *  order by test.c desc;
         */
        List<Integer> relevantChatUsers = chatRecordService.getRelevantChatUsers(sid);

        return relevantChatUsers.stream().skip((long) (page - 1) *pageSize).limit(pageSize).collect(Collectors.toMap(other->other, other->queryChatList(sid, other,count)));
    }

}
