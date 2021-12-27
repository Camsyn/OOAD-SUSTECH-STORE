package top.camsyn.store.commons.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName(value = "user_comment", autoResultMap = true)
public class UserComment {
    @TableId(value = "id",type = IdType.AUTO)
    Integer id;

    Integer toSid;

    Integer fromSid;

    String avatar;

    String content;

    Integer like_;

    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;

    Integer deleted;
}
