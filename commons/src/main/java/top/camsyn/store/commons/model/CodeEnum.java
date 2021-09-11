package top.camsyn.store.commons.model;

/**
 * @author Chen_Kunqiu
 */

public enum CodeEnum {
    SUCCESS(0),
    ERROR(1),
    PwdError(2);

    private Integer code;
    CodeEnum(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
