package top.camsyn.user.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.camsyn.user.rpc.fallback.AuthHystrix;

import java.util.Map;

@FeignClient(name = "auth", fallback = AuthHystrix.class)
public interface AuthClient {
    @PostMapping("/oauth/token")
    @ResponseBody
    Map<String,String> token(@RequestParam MultiValueMap<String, String> params);
}
