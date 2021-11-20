package top.camsyn.store.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.service.impl.UserService;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;

import javax.websocket.server.PathParam;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/get")
    public Result<User> getLoginUser() {
        log.info("获取登录用户");
        int loginSid = UaaHelper.getLoginSid();
        User user = userService.getOne(loginSid);
        if (user == null) return Result.failed("无此用户");
        return Result.succeed(user);
    }

    @GetMapping("/get/{sid}")
    public Result<User> getUser(@PathVariable("sid") Integer sid) {
        log.info("获取其他用户");
        User user = userService.getOne(sid);
        if (user == null) return Result.failed("无此用户");
        return Result.succeed(user);
    }

    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user) {
        log.info("更新用户信息，user: {}", user.getSid());
        if (UaaHelper.checkUser(user)) {
            User oldUser = userService.getOne(user.getSid());
            if (!oldUser.getHeadImage().equals(user.getHeadImage()) || !oldUser.getPayCode().equals(user.getPayCode())){
                // TODO: 2021/11/16 文件微服务校验持久化链接
            }
            return userService.updateById(user) ? Result.succeed(user, "成功更新") : Result.failed(user, "未知原因，更新失败");
        } else {
            return Result.failed(user, "你只能更新自己的用户信息!");
        }
    }




}
