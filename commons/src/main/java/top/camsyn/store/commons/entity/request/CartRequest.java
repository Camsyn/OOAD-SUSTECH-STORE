package top.camsyn.store.commons.entity.request;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest extends Request{
    @TableField(value = "cart_item_count")
    Integer cartItemCount;
    @TableField(value = "cart_item_id")
    Integer cartItemId;
    @TableField(value = "cart_item_owner")
    Integer owner;
    /**
     * 生成
     */
    @TableField(value = "cart_item_creat_time",fill = FieldFill.INSERT)
    LocalDateTime createTime;
    /**
     * 生成
     */
    @TableField(value = "cart_item_update_time",fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;

    public CartItem toCartItem(){
        return CartItem.builder().id(cartItemId).requestId(id).count(cartItemCount).owner(owner).build();
    }
}
