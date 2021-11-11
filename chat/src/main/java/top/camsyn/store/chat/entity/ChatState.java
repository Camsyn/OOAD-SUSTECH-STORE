package top.camsyn.store.chat.entity;


import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatState {
    /**
     * 0: 上线， 成功连接到chat服务器， 此时sid即你的sid
     * 1：下线，sid是下线者的sid，用于好友系统显示是否在线
     * 2. 成功发送信息，sid即你发送的对象（说明对象目前在线）
     * 3. 目标暂不在线
     */
    int state;
    int sid;
    String msg;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
