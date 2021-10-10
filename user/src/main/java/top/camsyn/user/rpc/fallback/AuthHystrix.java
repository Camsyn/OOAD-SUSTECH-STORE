package top.camsyn.user.rpc.fallback;

import org.springframework.util.MultiValueMap;
import top.camsyn.user.rpc.AuthClient;

import java.util.HashMap;
import java.util.Map;

public class AuthHystrix implements AuthClient {
    @Override
    public Map<String, String> token(MultiValueMap<String, String> map) {
        return new HashMap<>();
    }
}
