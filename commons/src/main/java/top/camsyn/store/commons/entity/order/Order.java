package top.camsyn.store.commons.entity.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@TableName("order")
@Builder
@ToString
public class Order {
    Integer id;
    /**
     * 发布请求者
     */
    Integer pusher;
    /**
     * 满足请求者（通常意义上的买家）
     */
    Integer puller;

    Integer count;
    /**
     * 请求的id
     */
    Integer requestId;

    /**
     * 0：订单准备，  1: 订单正式发布    2：订单正式满足，无法撤回
     */
    Integer state;

    /**
     * 不知道有什么用，留待拓展
     */
    String message;

    /**
     * 订单成功生成时间
     */
    LocalDateTime createTime;

    /**
     * 逻辑删除保留字段
     */
    Integer deleted;
}
