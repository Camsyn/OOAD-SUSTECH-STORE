package top.camsyn.store.uaa.exception;

import com.alibaba.rocketmq.client.exception.MQClientException;
import top.camsyn.store.uaa.enumeration.RocketMqErrorEnum;

public class RocketMqException extends Exception {

    public RocketMqException(RocketMqErrorEnum error, String context, boolean flag) {
        
    }

    public RocketMqException(MQClientException e) {
    }
}