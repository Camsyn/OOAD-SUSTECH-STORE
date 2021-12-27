package top.camsyn.store.commons.entity.chat;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("circle_message")
public class CircleMessage {
    /**
     * 自动生成的动态id（生成）
     */
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    /**
     * 动态标题
     */
    String topic;

    /**
     * tag
     */
    String tag;

    /**
     * 发动态者的sid （生成）
     */
    int sendId;
    /**
     * 动态内容，若是带有图片， 则以url的形式返回（在前端处理，前端先把图片上传了，得到返回的url，然后进行特殊标记，最后把纯文本上传到这里）
     */
    String content;

    /**
     * 动他类型， 后期以供拓展
     */
    int type;

    /**
     * 发动态的时间 （生成）
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime sendTime;

    private Integer deleted;
}
