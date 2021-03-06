package top.camsyn.store.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result<T> implements Serializable {

    private T data;
    private Integer resp_code;
    private String resp_msg;

    public boolean isSuccess(){
        return resp_code == 0;
    }

    public static <T> Result<T> succeed(String msg) {
        return of(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T data, String msg) {
        return of(data, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> succeed(T data) {
        return of(data, CodeEnum.SUCCESS.getCode(), "");
    }

    public static <T> Result<T> of(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }
    public static <T> Result<T> of(Integer code, String msg) {
        return new Result<>(null, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return of(data, CodeEnum.ERROR.getCode(), msg);
    }
}