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
    /**
     * 主键id (生成)
     */
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    /**
     * 发布请求者
     */
    Integer pusher;
    String pusherEmail;
    /**
     * 满足请求者（通常意义上的买家）
     */
    Integer puller;
    String pullerEmail;

    /**
     * '0:买， 1:卖,  其他待拓展'
     */
    Integer type;
    /**
     * '0: 第三方支付  1：平台代币  2. 个人收款码  3. 私下交易'
     */
    Integer tradeType;
    /**
     * 0: 商品   1：服务
     */
    Integer category;

    /**
     * 单价
     */
    Double singlePrice;
    /**
     * 交易数量
     */
    Integer tradeCnt;
    /**
     * 请求的id
     */
    Integer requestId;

    /**
     * 0：订单准备，  1: 订单正式发布    2：订单正式满足，无法撤回  3. 订单异常（审核中） 4. 订单中断（puller撤回）  5. 订单中断（pusher撤回）
     */
    @Builder.Default
    Integer state = 0;



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
    @Builder.Default
    Integer pullerConfirm = 0;
    /**
     * 请求发起者的状态
     */
    @Builder.Default
    Integer pusherConfirm = 0;

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

    public boolean isFinished() {
        return pullerConfirm == 1 && pusherConfirm == 1;
    }
}
