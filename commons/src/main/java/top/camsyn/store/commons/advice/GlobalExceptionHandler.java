package top.camsyn.store.commons.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.camsyn.store.commons.exception.NotFoundException;
import top.camsyn.store.commons.exception.NotSelfException;
import top.camsyn.store.commons.model.Result;

/**
 * 全局统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有未知异常
     */
    @ExceptionHandler(Exception.class)
    Result<Exception> handleException(Exception e){
        log.error("发生了未知异常",e);
        return Result.failed(e,"未知异常");
    }


    /**
     * 处理错误id的情况 （数据库找不到该元素）
     */
    @ExceptionHandler(NotFoundException.class)
    Result<Exception> handleNotFoundException(NotFoundException e){
        log.error("发生了找不到数据的异常",e);
        return Result.failed(e,"找不到该元素");
    }




    /**
     * 处理错误id的情况 （数据库找不到该元素）
     */
    @ExceptionHandler(NotSelfException.class)
    Result<Exception> handleNotSelfException(NotSelfException e){
        log.error("发生了非自己账户的异常",e);
        return Result.failed(e,"非本人，无权操作");
    }




}
