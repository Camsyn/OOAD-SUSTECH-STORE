package top.camsyn.store.commons.entity.user;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.*;
import top.camsyn.store.commons.handler.ListTypeHandler;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName(value = "account", autoResultMap = true)
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

    private String paycodePath;

    private Integer deleted;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Integer> follow;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private List<Integer> collection;

    public User dePrivacy(){
        liyuan = -1.0;
        collection = null;
        return this;
    }
}