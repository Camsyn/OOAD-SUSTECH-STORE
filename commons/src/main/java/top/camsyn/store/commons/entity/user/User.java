package top.camsyn.store.commons.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("account")
public class User extends Model<User> {
    /**
     * 生成
     */
    @TableId(value = "sid",type = IdType.AUTO)
    private Integer sid;
    private String email;
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String description;

    private Integer credit;

    private Double liyuan;

    private String headImage;

    private String payCode;

    private Integer deleted;
}