package top.camsyn.store.chat.service;


import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import top.camsyn.store.chat.entity.ChatState;
import top.camsyn.store.commons.entity.chat.ChatRecord;
import top.camsyn.store.commons.helper.UaaHelper;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/websocket/one2one/{username}")
public class WebSocket {

    @Autowired
    private ChatRecordService chatRecordService;

    /**
     * 在线人数
     */
    public static volatile AtomicInteger onlineNumber = new AtomicInteger(0);

    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static final Map<Integer, WebSocket> clients = new ConcurrentHashMap<>();

    /**
     * 会话
     */
    private Session session;

    /**
     * 用户id
     */
    private Integer sid;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("username") Integer sendId, Session session)
    {
        onlineNumber.addAndGet(1);
        log.info("现在来连接的客户id："+session.getId()+" 用户名："+sendId);
        this.sid = sendId;
        this.session = session;
        log.info("测试登录功能: sid: {}", UaaHelper.getLoginSid());
        log.info("有新连接加入！ 当前在线人数" + onlineNumber);
        try {
            //messageType 1代表上线 2代表下线 3代表在线名单 4代表普通消息
            //先给所有人发送通知，说我上线了
//            sendMessageAll(JSON.toJSONString(map1),sendId);
            //把自己的信息加入到map当中去
            clients.put(sendId, this);

            sendMessageTo(JSON.toJSONString(ChatState.builder().sid(sendId).state(0).msg("成功连接").build()),sendId);
            reply(ChatState.builder().msg("成功登录").sid(sendId).state(0).build().toString());

//            //给自己发一条消息：告诉自己现在都有谁在线
//            Map<String,Object> map2 = Maps.newHashMap();
//            map2.put("messageType",3);
//            //移除掉自己
//            Set<Integer> set = clients.keySet();
//            map2.put("onlineUsers",set);
//            sendMessageTo(JSON.toJSONString(map2),sendId);
        }
        catch (IOException e){
            log.info(sendId+"上线的时候通知所有人发生了错误");
        }



    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("服务端发生了错误"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose()
    {
        onlineNumber.addAndGet(-1);
        //webSockets.remove(this);
        clients.remove(sid);
//        try {
////            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
////            Map<String,Object> map1 = Maps.newHashMap();
////            map1.put("messageType",2);
////            map1.put("onlineUsers",clients.keySet());
////            map1.put("userId",sid);
////            sendMessageAll(JSON.toJSONString(map1),sid);
//        }
//        catch (IOException e){
//            log.info(sid+"下线的时候通知所有人发生了错误");
//        }
        log.info("有连接关闭！ 当前在线人数" + onlineNumber);
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        try {
            log.info("来自客户端消息：" + message+"客户端的id是："+session.getId());
            ChatRecord chatRecord = JSON.parseObject(message, ChatRecord.class);

            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            int recvId = chatRecord.getRecvId();
            if (isOnline(recvId)) {
                chatRecord.setRead(true);
                chatRecord.setRecvTime(LocalDateTime.now());
                sendMessageTo(JSON.toJSONString(chatRecord), recvId);
                reply(ChatState.builder().sid(recvId).state(2).msg("目标已接收").build().toString());
            }else {
                reply(ChatState.builder().sid(recvId).state(3).msg("目标未在线").build().toString());
            }
            chatRecordService.save(chatRecord);

        }
        catch (Exception e){
            log.info("发生了错误了");
        }

    }

    public void sendMessageTo(String message, Integer toSid) throws IOException {
        WebSocket webSocket = clients.get(toSid);
        webSocket.session.getBasicRemote().sendText(message);
//        for (WebSocket item : clients.values()) {
//            if (item.username.equals(toSid) ) {
//                item.session.getAsyncRemote().sendText(message);
//                break;
//            }
//        }
    }

    public void sendMessageAll(String message,Integer FromUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized AtomicInteger getOnlineCount() {
        return onlineNumber;
    }

    public static boolean isOnline(int sid){
        return clients.containsKey(sid);
    }

    public void reply(String msg) throws IOException {
        sendMessageTo(msg, this.sid);
    }

}
