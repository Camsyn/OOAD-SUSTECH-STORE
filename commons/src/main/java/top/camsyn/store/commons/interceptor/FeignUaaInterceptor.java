package top.camsyn.store.commons.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.commons.constant.AuthConstant;
import top.camsyn.store.commons.exception.AuthException;
import top.camsyn.store.commons.helper.UaaHelper;

@Slf4j
@Configuration
public class FeignUaaInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("rpc调用中");
        String userStr;
        try {
            userStr = UaaHelper.getCurrentUserStr();
        }catch (Exception e){
            log.info("当前线程无user数据, 装填默认user(debug用)");
            userStr = "{\"sid\":11911626, \"user_name\":\"11911626\", \"password\":\"123456\",\"email\":\"11911626@mail.susetch.edu.cn\",\"status\":0,\"roles\":[\"admin\",\"root\"]}";
        }
        requestTemplate.header(AuthConstant.UAA_HEADER, userStr);
    }
}
