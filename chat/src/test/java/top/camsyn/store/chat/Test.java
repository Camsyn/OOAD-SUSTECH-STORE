package top.camsyn.store.chat;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import top.camsyn.store.chat.entity.ChatState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, LocalDateTime> map = new HashMap<>();
        map.put("time",LocalDateTime.now());
        System.out.println(JSON.toJSONString(map));
        LocalDateTime localDateTime = JSON.parseObject("\"2021-11-11T19:45:15.445\"", LocalDateTime.class);
        System.out.println(localDateTime.getDayOfMonth());
    }
}
