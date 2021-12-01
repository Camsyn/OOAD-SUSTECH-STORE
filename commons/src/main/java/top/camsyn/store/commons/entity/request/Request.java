package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@TableName(value = "request", autoResultMap = true)
@ToString
public class Request {
    /**
     * 主键id （生成）
     */
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    String title;
    String description;
    /**
     * 发布请求者（生成）
     */
    Integer pusher;
    /**
     * '0:买， 1:卖,  其他待拓展'
     */
    Integer type;
    /**
     * 总量
     */
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
     * '0: 未审核  1: 审核中 2：开启， 3：关闭   4. 隐藏   5. 封禁'  （生成）
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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime pushTime;

    /**
     * 路径序列
     */
    @TableField(typeHandler = FastjsonTypeHandler.class)
    List<String> images;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    List<String> video;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    List<String> labels;

    private Integer deleted;
    public boolean isLiyuanPaySellReq(){
        return tradeType==1 && type==1;
    }
    public boolean isLiyuanPayBuyReq(){
        return tradeType==1 && type==0;
    }
}
