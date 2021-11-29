package top.camsyn.store.commons.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class UserDto {
    private Integer sid;
    @JSONField(name = "user_name")
    private String username;
    private String password;
    private Integer status;
    @JSONField(name = "authorities")
    private List<String> roles;
}
