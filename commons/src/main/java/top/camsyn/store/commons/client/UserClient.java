package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.UserHystrix;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.model.Result;

@FeignClient(name = "auth", fallback = UserHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.auth.controller.UserController")
@RequestMapping("/user")
@ResponseBody
public interface UserClient {
    @GetMapping("/get")
    Result<User> getLoginUser();

    @GetMapping("/get/{sid}")
    Result<User> getUser(@PathVariable("sid") Integer sid);

    @PutMapping("/update")
    Result<User> updateUser(@RequestBody User user);
}


