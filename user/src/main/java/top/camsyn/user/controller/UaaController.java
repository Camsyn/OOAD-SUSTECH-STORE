package top.camsyn.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import top.camsyn.user.rpc.AuthClient;

import java.util.Map;

public class UaaController {
    @Autowired
    AuthClient authClient;

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("client_secret", "123456");
        map.add("client_id", "store");
        map.add("grant_type", "password");
        Map<String,String> resp = authClient.token(map);
        String access_token = resp.get("access_token");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8081/admin/hello", HttpMethod.GET, httpEntity, String.class);
//        model.addAttribute("msg", entity.getBody());
        return "index";
    }
}
