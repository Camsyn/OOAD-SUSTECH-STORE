package top.camsyn.store.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.*;
import top.camsyn.store.auth.service.impl.UserService;
import top.camsyn.store.commons.entity.user.User;
import top.camsyn.store.commons.helper.UaaHelper;
import top.camsyn.store.commons.model.Result;
import top.camsyn.store.commons.model.UserDto;

import javax.websocket.server.PathParam;
import java.sql.ResultSet;
import java.util.List;

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
        if (user == null) {
            log.info("无此用户");
            return Result.failed("无此用户");
        }
        log.info("成功获取登录用户{}", user);
        return Result.succeed(user);
    }

    @GetMapping("/get/{sid}")
    public Result<User> getOtherUser(@PathVariable("sid") Integer sid) {
        log.info("获取指定用户信息（去隐私后的）");
        User user = userService.getOne(sid);
        if (user == null) {
            log.info("无此用户");
            return Result.failed("无此用户");
        }
        user.dePrivacy();
        log.info("成功获取指定用户信息（去隐私后的）{}", user);
        return Result.succeed(user);
    }

    @GetMapping("/getRandom")
    public Result<List<User>> getRandomUsers(@RequestParam("size") Integer size) {
        log.info("随机获取用户（去隐私后的）");
        final List<User> randomUsers = userService.getRandomUsers(UaaHelper.getLoginSid(), size);
        log.info("成功随机获取用户（去隐私后的: {}）", randomUsers);
        return Result.succeed(randomUsers);
    }


    @GetMapping("/avatar/list")
    public Result<List<String>> getAvatarList(@RequestParam("size") List<Integer> sid) {
        log.info("批量获取头像");
        final List<String> avatars = userService.getAvatarBatch(sid);
        log.info("成功批量获取头像");
        return Result.succeed(avatars);
    }


    @GetMapping("/")
    public Result<UserDto> getCurrentUser() {
        log.info("获取登录用户");
        return Result.succeed(UaaHelper.getCurrentUser());
    }

    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user) {
        log.info("更新用户信息，user: {}", user.getSid());
        UaaHelper.assertAdmin(user.getSid());

//        User oldUser = userService.getOne(user.getSid());
//        if (!oldUser.getHeadImage().equals(user.getHeadImage()) || !oldUser.getPaycodePath().equals(user.getPaycodePath())) {
//            // TODO: 2021/11/16 文件微服务校验持久化链接
//        }
        if (userService.updateById(user)) {
            log.info("成功更新{}", user);
            return Result.succeed(user, "成功更新");
        } else {
            log.info("未知原因，更新失败{}", user);
            return Result.failed(user, "未知原因，更新失败");
        }
    }

    @GetMapping("/rpc/get/{sid}")
    public Result<User> getUser(@PathVariable("sid") Integer sid) {
        log.info("获取其他用户");
        User user = userService.getOne(sid);
        log.info("获取成功{}", user);
        return Result.succeed(user);
    }

    @PutMapping("/rpc/update")
    public Result<User> update(@RequestBody User user) {
        if (userService.updateById(user)) {
            log.info("成功更新{}", user);
            return Result.succeed(user, "成功更新");
        } else {
            log.info("未知原因，更新失败{}", user);
            return Result.failed(user, "未知原因，更新失败");
        }
    }

    @PutMapping("/rpc/changeCredit")
    public Result<User> changeCredit(@RequestParam("sid") Integer sid, @RequestParam("delta") Integer delta) {
        log.info("开始修改信誉分");
        final User user = userService.changeCredit(sid, delta);
        log.info("修改信誉分成功");
        return Result.succeed(user, "修改信誉分成功");
    }

    @PutMapping("/rpc/changeLiyuan")
    public Result<User> changeLiyuan(@RequestParam("sid") Integer sid, @RequestParam("delta") Double delta) {
        log.info("开始修改余额");
        final User user = userService.changeLiyuan(sid, delta);
        log.info("修改余额成功");
        return Result.succeed(user, "修改余额成功");
    }

    @PutMapping("/rpc/onetrade")
    public Result<Boolean> changeLiyuan(@RequestParam("adder") Integer adder,
                                        @RequestParam("subscriber") Integer subscriber, @RequestParam("delta") Double delta) {
        log.info("开始修改余额");
        userService.changeLiyuan(adder, subscriber, delta);
        log.info("修改余额成功");
        return Result.succeed(true, "修改余额成功");
    }


    @PutMapping("/rpc/state/modify")
    public Result<User> modifyUserState(@RequestParam("sid") Integer sid, @RequestParam("state") Integer state) {
        log.info("更新账户的状态");
        final User one = userService.getOne(sid);
        one.setSid(state);
        userService.updateById(one);
        log.info("修改账户状态成功");
        return Result.succeed(one);
    }

}
