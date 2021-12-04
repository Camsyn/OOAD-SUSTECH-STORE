package top.camsyn.store.commons.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import top.camsyn.store.commons.constant.AuthConstant;
import top.camsyn.store.commons.helper.UaaHelper;

@Configuration
public class FeignUaaInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        final String userStr = UaaHelper.getUserStr();
        requestTemplate.header(AuthConstant.UAA_HEADER, userStr);
    }
}
