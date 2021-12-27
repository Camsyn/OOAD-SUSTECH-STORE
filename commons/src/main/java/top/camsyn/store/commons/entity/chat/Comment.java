package top.camsyn.store.commons.entity.chat;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 动态的评论
 */
@Data
@TableName("comment")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Comment {
    /**
     * 自动生成的id （生成）
     */
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;
    /**
     * 外键，导向动态的id
     */
    int cmId;
    /**
     * 评论内容
     */
    String content;
    /**
     * 类型，供后续拓展
     */
    int type;
    /**
     * 发送者id （生成）
     */
    int sendId;
    /**
     * 发评论时间（生成）
     */
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime sendTime;

    Integer like_;

    private Integer deleted;
}
