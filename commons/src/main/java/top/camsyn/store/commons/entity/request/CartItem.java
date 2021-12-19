package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    /**
     * 生成
     */
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    Integer owner;
    /**
     * 生成
     */
    Integer requestId;
    /**
     * 购物车条目状态： 0: 正常  1: 完成购买   2: 被清空
     */
    @Builder.Default
    Integer state=0;

    @Builder.Default
    Integer count = 1;

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

    private Integer deleted;
}
