package top.camsyn.store.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.camsyn.store.chat.entity.ChatRecord;
import top.camsyn.store.chat.service.ChatRecordService;
import top.camsyn.store.chat.service.ChatService;
import top.camsyn.store.chat.service.WebSocket;
import top.camsyn.store.commons.model.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController("/chat")
public class ChatController {

    @Autowired
    private ChatRecordService chatRecordService;

    @Autowired
    private ChatService chatService;

    /**
     * 多发送一条记录， 以表明是否有别以前还有未读的消息
     * @param sendSid
     * @param recvSid
     * @param count
     * @return
     */
    @GetMapping("/record/getLatest")
    public Result<List<ChatRecord>> getChatListFromSid(@RequestParam("sendSid")Integer sendSid,
                                                       @RequestParam("recvSid") Integer recvSid,
                                                       @RequestParam("count") Integer count){
        log.info("请求聊天记录， s_sid: {}, r_sid: {}, count: {}",sendSid,recvSid,count);
//        chatRecordService.page()
        try{

            List<ChatRecord> chatRecords = chatService.queryChatList(sendSid, recvSid, count+1);
            updateRecords(count, chatRecords);
            return Result.succeed(chatRecords);
        }catch (Exception e){
            log.error("发生错误， 请求聊天记录， s_sid: {}, r_sid: {}, count: {}",sendSid,recvSid,count);
            return Result.failed("服务器错误");
        }
    }
    /**
     * 多发送一条记录， 以表明是否有别以前还有未读的消息
     */
    @GetMapping("/record/getElse")
    public Result<List<ChatRecord>> getChatListFromSidAndTime(@RequestParam("sendSid")Integer sendSid,
                                                       @RequestParam("recvSid") Integer recvSid,
                                                       @RequestParam("before") LocalDateTime timeBefore,
                                                       @RequestParam("count") Integer count){
        log.info("请求更多聊天记录， s_sid: {}, r_sid: {}, before: {},  count: {}",sendSid,recvSid,timeBefore,count);
//        chatRecordService.page()
        try{
            List<ChatRecord> chatRecords = chatService.queryChatList(sendSid, recvSid, count+1);
            updateRecords(count, chatRecords);
            return Result.succeed(chatRecords);
        }catch (Exception e){
            log.error("错误！ 请求更多聊天记录， s_sid: {}, r_sid: {}, before: {},  count: {}",sendSid,recvSid,timeBefore,count);
            return Result.failed("服务器错误");
        }
    }

//    @GetMapping("/record/get")
//    public Result<List<ChatRecord>> getChatListFromSid(@RequestParam("sendSid")Integer sendSid,
//                                                       @RequestParam("recvSid") Integer recvSid,
//                                                       @RequestParam("count") Integer count){
//        log.info("请求聊天记录， s_sid: {}, r_sid: {}, count: {}",sendSid,recvSid,count);
////        chatRecordService.page()
//        try{
//
//            List<ChatRecord> chatRecords = chatService.queryChatList(sendSid, recvSid, count);
//            chatRecords.stream().filter(i->!i.isRead()).forEach(i->
//            {
//                i.setSendTime(new Date());
//                i.setRead(true);
//            });
//            chatRecordService.updateBatchById(chatRecords);
//            return Result.succeed(chatRecords);
//        }catch (Exception e){
//            log.error("发生错误， 请求聊天记录， s_sid: {}, r_sid: {}, count: {}",sendSid,recvSid,count);
//            return Result.failed("服务器错误");
//        }
//    }

    /**
     * 多发送一条记录， 以表明是否有别以前还有未读的消息
     */
    @GetMapping("/record/getAll")
    public Result<Map<Integer, List<ChatRecord>>> getChatMap(@RequestParam("recvSid") Integer recvSid,
                                                       @RequestParam("user-size") Integer userSize,
                                                       @RequestParam("count") Integer count){
        log.info("请求一个用户所有聊天记录聊天记录， r_sid: {}, user-size: {} ,count: {}",recvSid,userSize,count);
//        chatRecordService.page()
        try{
            Map<Integer, List<ChatRecord>> result = chatService.queryAllLatestChatList(recvSid, userSize, count+1);

            result.forEach((key,chatRecords)->{
                updateRecords(count, chatRecords);
            });
            return Result.succeed(result);
        }catch (Exception e){
            log.error("发生错误， 请求一个用户所有聊天记录聊天记录， r_sid: {}, user-size: {} ,count: {}",recvSid,userSize,count);
            return Result.failed("服务器错误");
        }
    }

    @GetMapping("/is/online")
    public Result<Boolean> isOnline(@RequestParam("sid") Integer sid){
        log.info("查询用户是否online, sid: {}",sid);
        try{
            return Result.succeed(WebSocket.isOnline(sid));
        }catch (Exception e){
            log.error("查询用户是否online, sid: {}",sid);
        }
        return Result.failed("异常");

    }
    @GetMapping("/are/online")
    public Result<List<Boolean>> areOnline(@RequestParam("sid") List<Integer> sid){
        log.info("查询用户是否online, sid: {}",sid);
        try{
            return Result.succeed(sid.stream().map(WebSocket::isOnline).collect(Collectors.toList()));
        }catch (Exception e){
            log.error("查询用户是否online, sid: {}",sid);
        }
        return Result.failed("异常");

    }

    private void updateRecords(@RequestParam("count") Integer count, List<ChatRecord> chatRecords) {
        chatRecords.stream().filter(i->!i.isRead()).forEach(i->
        {
            i.setSendTime(LocalDateTime.now());
            i.setRead(true);
        });
        if (chatRecords.size() == count +1){
            chatRecords.get(count).setRead(false);
            chatRecords.get(count).setRecvTime(null);
        }
        chatRecordService.updateBatchById(chatRecords);
    }
//    @GetMapping("/broadcast")
//    public Result<Boolean> getChatListFromSid(@RequestParam("sendSid")Integer sendSid,
//                                                       @RequestParam("recvSid") Integer recvSid,
//                                                       @RequestParam("count") Integer count){
//
//    }
}
