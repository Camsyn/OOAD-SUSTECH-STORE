package top.camsyn.store.commons.model;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Integer sid;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}
