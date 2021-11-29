package top.camsyn.store.auth.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.camsyn.store.commons.model.Result;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public Result<OAuth2Exception> handleOauth2(OAuth2Exception e) {
        return Result.failed(e, e.getMessage());
    }
}
