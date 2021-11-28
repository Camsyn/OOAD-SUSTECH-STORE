package top.camsyn.store.auth.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Model<Role> implements GrantedAuthority {
    String role;
    int owner;

    @TableField(fill = FieldFill.INSERT)
    Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Date updateTime;
    Date remainTime;

    private Integer deleted;
    @Override
    public String getAuthority() {
        return role;
    }
}
