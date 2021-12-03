package top.camsyn.store.commons.entity.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("trade_record")
@Builder
@ToString
public class TradeRecord {
    //    id serial primary key,
//    pusher int,
//    puller int,
//    req_id int,
//    trade_cnt int,
//    pusher_ensure int,
//    puller_ensure int,
//    create_time datetime,
//    update_time datetime,
//    type int,
//    deleted bool default 0
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    /**
     * 发布请求者
     */
    Integer pusher;
    /**
     * 满足请求者（通常意义上的买家）
     */
    Integer puller;

    /**
     * 留待拓展
     */
    Integer type;

    Integer tradeCnt;
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
     * 请求的名称
     */
    String requestTitle;
    /**
     * 请求拉取者的状态
     */
    Integer pullerEnsure;
    /**
     * 请求发起者的状态
     */
    Integer pusherEnsure;

    /**
     * 订单成功生成时间
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;

    /**
     * 订单成功生成时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;

    /**
     * 逻辑删除保留字段
     */
    Integer deleted;
}
