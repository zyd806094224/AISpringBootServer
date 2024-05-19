package com.example.aiserver.controller;

import com.example.aiserver.entity.User;
import com.example.aiserver.service.UserService;
import com.example.aiserver.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    public static ConcurrentHashMap<String, User> loginUser = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Long> loginUserKey = new ConcurrentHashMap<>();

    @RequestMapping("/login")
    public Result login(String username, String password) {
        if (username == null) return Result.fail("必须填写用户名");

        User user = userService.queryByName(username);
        if (user == null) return Result.fail("用户名不存在");
        String targetPassword = user.getPassword();
        if (targetPassword == null) return Result.fail("用户密码异常");
        if (!targetPassword.equals(password)) return Result.fail("密码错误");

        loginUser.put(username, user);
        loginUserKey.put(username, System.currentTimeMillis());
        return Result.ok(String.valueOf(loginUserKey.get(username)));
    }

    @RequestMapping("/logout")
    public Result logout(String username) {
        loginUser.remove(username);
        loginUserKey.remove(username);
        return Result.ok();
    }

    @RequestMapping("/checkUserKey")
    public Result checkUserKey(String username, Long key) {
        if (username == null || key == null) return Result.fail("用户校验异常");
        if (!Objects.equals(loginUserKey.get(username), key)) {
            return Result.fail("用户在其他地方登录！！！");
        }
        return Result.ok();
    }

    @RequestMapping("/loginUser")
    public Result loginUser() {
        return Result.ok("success", loginUser.keySet());
    }
}
