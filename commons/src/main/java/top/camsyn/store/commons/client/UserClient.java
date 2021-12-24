package top.camsyn.store.commons.client;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.commons.client.callback.UserHystrix;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.model.Result;

@FeignClient(value = "auth",fallback = UserHystrix.class)
@ConditionalOnMissingClass("top.camsyn.store.auth.controller.UserController")
@RequestMapping("/user")
@ResponseBody
public interface UserClient {


    @GetMapping("/rpc/get/{sid}")
    Result<User> getUser(@PathVariable("sid") Integer sid);

    @PutMapping("/rpc/update")
    Result<User> updateUser(@RequestBody User user);

    @PutMapping("/rpc/changeLiyuan")
    Result<User> changeLiyuan(@RequestParam("sid") Integer sid, @RequestParam("delta") Double delta);

    @PutMapping("/rpc/onetrade")
    Result<Boolean> changeLiyuan(@RequestParam("adder") Integer adder,
                                     @RequestParam("subscriber")Integer subscriber, @RequestParam("delta") Double delta);


    @PutMapping("/rpc/state/modify")
    Result<User> modifyUserState(@RequestParam("sid")Integer sid, @RequestParam("state") Integer state);
}



