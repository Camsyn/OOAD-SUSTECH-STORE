package top.camsyn.store.chat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.chat.service.ChatRecordService;
import top.camsyn.store.chat.service.ChatService;
import top.camsyn.store.chat.service.WebSocket;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController()
@RequestMapping("/private-talk")
public class ChatController {

    @Autowired
    private ChatRecordService chatRecordService;

    @Autowired
    private ChatService chatService;



    /**
     * 分页查询聊天记录, 这种不会多返回一条聊天记录
     */
    @GetMapping("/record/getPage")
    public Result<List<ChatRecord>> getChatPageFromSid(@RequestParam("other") Integer other,
                                                       @RequestParam("pageSize") Integer pageSize,
                                                       @RequestParam("page") Integer page) {
        int mySid = UaaHelper.getLoginSid();
        log.info("请求聊天记录， mySid: {}, o_sid: {}, pageSize: {}, page: {}", mySid, other, pageSize,page);
        try {
            IPage<ChatRecord> chatPage = chatService.queryChatPage(mySid, other, page, pageSize);
            List<ChatRecord> chatRecords = chatPage.getRecords();
            updateRecords(pageSize, chatRecords);
            log.info("请求聊天记录成功");
            return Result.succeed(chatRecords);
        } catch (Exception e) {
            log.error("请求聊天记录， mySid: {}, o_sid: {}, pageSize: {}, page: {}", mySid, other, pageSize,page);

            return Result.failed("服务器错误");
        }
    }


    /**
     * 多发送一条记录， 以表明是否有别以前还有未读的消息
     */
    @GetMapping("/record/getLatest")
    public Result<List<ChatRecord>> getChatListFromSid(@RequestParam("other") Integer other,
                                                       @RequestParam("count") Integer count) {
        int mySid = UaaHelper.getLoginSid();
        log.info("请求聊天记录， mySid: {}, o_sid: {}, count: {}", mySid, other, count);
//        chatRecordService.page()
        try {
            List<ChatRecord> chatRecords = chatService.queryChatList(mySid, other, count + 1);
            updateRecords(count, chatRecords);
            log.info("请求聊天记录成功");
            return Result.succeed(chatRecords);
        } catch (Exception e) {
            log.error("发生错误， 请求聊天记录， mySid: {}, o_sid: {}, count: {}", mySid, other, count);
            return Result.failed("服务器错误");
        }
    }




    /**
     * 多发送一条记录， 以表明是否有别以前还有未读的消息
     */
    @GetMapping("/record/getElse")
    public Result<List<ChatRecord>> getChatListFromSidAndTime(@RequestParam("other") Integer other,
                                                              @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                      LocalDateTime timeBefore,
                                                              @RequestParam("count") Integer count) {
        int mySid = UaaHelper.getLoginSid();
        log.info("请求更多聊天记录， mySid: {}, other: {}, before: {},  count: {}", mySid, other, timeBefore, count);
//        chatRecordService.page()
        try {
            List<ChatRecord> chatRecords = chatService.queryChatList(mySid, other, count + 1);
            updateRecords(count, chatRecords);
            log.info("请求更多聊天记录成功");
            return Result.succeed(chatRecords);
        } catch (Exception e) {
            log.error("错误！ 请求更多聊天记录， s_sid: {}, r_sid: {}, before: {},  count: {}", mySid, other, timeBefore, count);
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
     *
     * @param pageSize 分页大小
     * @param page     第几页
     * @param count    页内每个 List<ChatRecord> 的大小
     * @return 对方的sid为key， 相关聊天记录为value
     */
    @GetMapping("/record/getAll")
    public Result<Map<Integer, List<ChatRecord>>> getChatMap(@RequestParam("pageSize") Integer pageSize,
                                                             @RequestParam("page") Integer page,
                                                             @RequestParam("count") Integer count) {
        int mySid = UaaHelper.getLoginSid();
        log.info("请求一个用户所有聊天记录聊天记录， mySid: {}, pageSize: {} , page:{}, count: {}", mySid, pageSize, page, count);
//        chatRecordService.page()
        try {
            Map<Integer, List<ChatRecord>> result = chatService.queryAllLatestChatList(mySid, pageSize, page, count + 1);

            result.forEach((key, chatRecords) -> {
                updateRecords(count, chatRecords);
            });
            log.info("请求一个用户所有聊天记录成功");
            return Result.succeed(result);
        } catch (Exception e) {
            log.error("发生错误， 请求一个用户所有聊天记录聊天记录， r_sid: {}, user-size: {} ,count: {}", mySid, pageSize, count);
            return Result.failed("服务器错误");
        }
    }

    @GetMapping("/is/online")
    public Result<Boolean> isOnline(@RequestParam("sid") Integer sid) {
        log.info("查询用户是否online, sid: {}", sid);
        try {
            return Result.succeed(WebSocket.isOnline(sid));
        } catch (Exception e) {
            log.error("查询用户是否online, sid: {}", sid);
        }
        return Result.failed("异常");

    }

    @GetMapping("/are/online")
    public Result<List<Boolean>> areOnline(@RequestBody List<Integer> sid) {
        log.info("查询用户是否online, sid: {}", sid);
        try {
            return Result.succeed(sid.stream().map(WebSocket::isOnline).collect(Collectors.toList()));
        } catch (Exception e) {
            log.error("查询用户是否online, sid: {}", sid);
        }
        return Result.failed("异常");

    }

    private void updateRecords(Integer count, List<ChatRecord> chatRecords) {
        boolean flag = false;
        if (chatRecords.size() == count + 1) {
            ChatRecord record = chatRecords.get(count);
            if (!record.isRead()) {
                flag = true;
            }
        }
        chatRecords.stream().filter(i -> !i.isRead()).peek(i ->
        {
            i.setRecvTime(LocalDateTime.now());
            i.setRead(true);
        }).findFirst().ifPresent(i->log.info("更新聊天记录接收状态：send: {}, recv: {}, time: {}", i.getSendId(),i.getRecvId(), i.getRecvTime()));
        if (flag) {
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
