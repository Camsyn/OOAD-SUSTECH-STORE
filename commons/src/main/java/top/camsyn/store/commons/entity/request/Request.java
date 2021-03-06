package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import lombok.experimental.Accessors;
import top.camsyn.store.commons.handler.ListTypeHandler;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "request", autoResultMap = true)
@ToString
public class Request {
    /**
     * 主键id （生成）
     */
    @TableId(value = "id", type = IdType.AUTO)
    Integer id;
    String title;
    String desc_;
    /**
     * 发布请求者（生成）
     */
    Integer pusher;
    String pusherEmail;
    /**
     * '0:买， 1:卖,  其他待拓展'
     */
    Integer type;
    /**
     * 总量
     */
    @TableField("count_")
    Integer count;
    /**
     * 已售数量
     */
    Integer saleCount;
    /**
     * '0: 第三方支付  1：平台代币  2. 个人收款码  3. 私下交易'
     */
    Integer tradeType;
    /**
     * '未审核  1: 审核中 0: 2：开启， 3：关闭   4. 隐藏   5. 封禁'  （生成）
     */
    Integer state;
    /**
     * 0: 商品   1：服务
     */
    Integer category;
    /**
     * 准确价格
     */
    Double exactPrice;
    /**
     * 原价
     */
    Double originalPrice;
    /**
     * 预估价格（用于私下交易）
     */
    Double floatPriceFrom;
    Double floatPriceTo;

    /**
     * 生成
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    /**
     * 生成
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    /**
     * 生成, 审核通过，正式发布的时间
     */
    LocalDateTime pushTime;

    /**
     * 路径序列
     */
    @TableField(typeHandler = ListTypeHandler.class)
    List<String> images;
    @TableField(typeHandler = ListTypeHandler.class)
    List<String> video;
    @TableField(typeHandler = ListTypeHandler.class)
    List<String> labels;

    private Integer deleted;

    public boolean liyuanPaySellReq() {
        return tradeType == 1 && type == 1;
    }

    public boolean liyuanPayBuyReq() {
        return tradeType == 1 && type == 0;
    }
}
