package top.camsyn.store.chat.service;


import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.JWSObject;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@ServerEndpoint("/websocket/one2one")
public class WebSocket {
    static Pattern token = Pattern.compile("(?<=token=)(?<token>.*)");
    private static ChatRecordService chatRecordService;

    @Autowired
    public void setChatRecordService(ChatRecordService chatRecordService){
        WebSocket.chatRecordService = chatRecordService;
    }

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
    public void onOpen(Session session) {
        onlineNumber.addAndGet(1);
        log.info("尝试建立连接");
//        log.info("现在来连接的客户id：" + session.getId() + " 用户名：" + sendId);
//        this.sid = sendId;
        this.session = session;
        log.info("有新连接加入！ 当前在线人数" + onlineNumber);
        String rawQuery = session.getRequestURI().getRawQuery();
        Matcher matcher = token.matcher(rawQuery);
        try {
            if (matcher.find()) {
                String token = matcher.group("token");
                JWSObject jwsObject = null;
                try {
                    jwsObject = JWSObject.parse(token);
                } catch (ParseException e) {
                    reply("登陆失败");
                    session.close();
                }
                String userStr = jwsObject.getPayload().toString();
                log.info("解析后的jwt：{}", userStr);

                Integer sid = UaaHelper.getUser(userStr).getSid();
                clients.put(sid, this);
                this.sid = sid;
                reply(ChatState.builder().msg("成功登录").sid(sid).state(0).build().toString());
            } else {
                reply("登陆失败, 查无token");
                session.close();
            }
        } catch (IOException e) {
            log.error("websocket连接中断");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("服务端发生了错误" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
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
    public void onMessage(String message, Session session) {
        try {
            log.info("来自客户端消息：" + message + "  客户端的id是：" + session.getId());
            ChatRecord chatRecord = JSON.parseObject(message, ChatRecord.class);

            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            int recvId = chatRecord.getRecvId();
            chatRecord.setSendId(sid);
            if (isOnline(recvId)) {
                chatRecord.setRead(true);
                chatRecord.setRecvTime(LocalDateTime.now());
                sendMessageTo(JSON.toJSONString(chatRecord), recvId);
                reply(ChatState.builder().sid(recvId).state(2).msg("目标已接收").build().toString());
            } else {
                reply(ChatState.builder().sid(recvId).state(3).msg("目标未在线").build().toString());
            }
            chatRecordService.save(chatRecord);

        } catch (Exception e) {
            log.error("发生了错误了", e);
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

    public void sendMessageAll(String message, Integer FromUserName) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    public static synchronized AtomicInteger getOnlineCount() {
        return onlineNumber;
    }

    public static boolean isOnline(int sid) {
        return clients.containsKey(sid);
    }

    public void reply(String msg) throws IOException {
        sendMessageTo(msg, this.sid);
    }

}
