//package top.camsyn.store.chat.service;
//
//import org.springframework.stereotype.Service;
//
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Objects;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
//
///**
// * @author Chen_Kunqiu
// *
// * 这里稍微解释一下这些主要注解的作用。
// * ·@ServerEndpoint("/ws"):表示让 Spring创建WebSocket的服务端点，其中请求地址是“/ws"。
// * ·@OnOpen:标注客户端打开WebSocket服务端点调用方法。
// * ·@OnClose:标注客户端关闭WebSocket服务端点调用方法。
// * ·@OnMessage:标注客户端发送消息，WebSocket服务端点调用方法。
// * ·@OnError:标注客户端请求WebSocket服务端点发生异常调用方法。
// */
//@ServerEndpoint("/ws")
//@Service
//public class WebSocketService {
//    //静态变量,用来记录当前在线连接数。应该把它设计成线程安全的
//    private static int onlineCount = 0;
//    // concurrent包的线程安全set,用来存放每个客户端对应的 WebSocketService对象
//    private static CopyOnWriteArraySet<WebSocketService>
//            webSocketSet = new CopyOnWriteArraySet<>();
//
//    private static final Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
//    //与某个客户端的连接会话,需要通过它来给客户端发送数据
//    private Session session;
//
//    /*连接建立成功调用的方法*/
//    @OnOpen
//    public void onOpen(Session session) {
//        this.session = session;
//        session.getBasicRemote().sendText();
//
//        webSocketSet.add(this); //加入set中
//        addOnlineCount();  //在线数加1
//        System.out.println("有新连接加入!当前在线人数为" + getOnlineCount());
//        try {
//            sendMessage("有新的连接加入了!!");
//        } catch (IOException e) {
//            System.out.println("Io异常");
//        }
//    }
//
//    /*连接关闭调用的方法*/
//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this);//从set中删除
//        subOnlineCount();
//        //在线数减1
//        System.out.println("有一连接关闭!当前在线人数为" +
//                getOnlineCount());
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session){
//        System.out.println("来自客户端的消息："+message);
//
//        // 群发消息
//        for (WebSocketService webSocketService : webSocketSet) {
//            try{
//              /*  //获取当前用户名称
//                String userNamee = webSocketService.getSession().getUserPrincipal().getName();
//                System.out.println(userNamee);*/
//                webSocketService.sendMessage(message);
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 发生错误时调用
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        System.out.println("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 发送消息
//     *
//     * @param message 客户端消息
//     * @throws IOException
//     */
//    private void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    //返回在线数
//    private static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    //当连接人数增加时
//    private static synchronized void addOnlineCount() {
//        WebSocketService.onlineCount++;
//    }
//
//    //当连接人数减少时
//    private static synchronized void subOnlineCount() {
//        WebSocketService.onlineCount--;
//    }
//
//    public static void setOnlineCount(int onlineCount) {
//        WebSocketService.onlineCount = onlineCount;
//    }
//
//    public static CopyOnWriteArraySet<WebSocketService> getWebSocketSet() {
//        return webSocketSet;
//    }
//
//    public static void setWebSocketSet(CopyOnWriteArraySet<WebSocketService> webSocketSet) {
//        WebSocketService.webSocketSet = webSocketSet;
//    }
//
//    public Session getSession() {
//        return session;
//    }
//
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        WebSocketService that = (WebSocketService) o;
//        return Objects.equals(session, that.session);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(session);
//    }
//}
