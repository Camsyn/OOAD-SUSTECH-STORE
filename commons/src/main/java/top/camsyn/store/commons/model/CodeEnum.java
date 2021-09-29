package top.camsyn.store.commons.model;

/**
 * @author Chen_Kunqiu
 */

public enum CodeEnum {
    SUCCESS(0),
    ERROR(1),
    LoginFail(2),
    NoPermission(3);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
