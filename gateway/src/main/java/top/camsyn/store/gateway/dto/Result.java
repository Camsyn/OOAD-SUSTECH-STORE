package top.camsyn.store.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private T data;
    private Integer resp_code;
    private String resp_msg;

    public boolean isSuccess(){
        return resp_code == 0;
    }

    public static <T> Result<T> succeed(String msg) {
        return of(null, 0, msg);
    }

    public static <T> Result<T> succeed(T data, String msg) {
        return of(data, 0, msg);
    }

    public static <T> Result<T> succeed(T data) {
        return of(data, 0, "");
    }

    public static <T> Result<T> of(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }
    public static <T> Result<T> of(Integer code, String msg) {
        return new Result<>(null, code, msg);
    }

    public static <T> Result<T> failed(String msg) {
        return of(null, 1, msg);
    }

    public static <T> Result<T> failed(T data, String msg) {
        return of(data, 1, msg);
    }
}