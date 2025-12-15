package com.campus.controller;

import com.campus.model.User;
import com.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user") // 用户相关的接口都放在 /api/user 下
public class UserController {

    @Autowired
    private UserService userService; // 注入 UserService

    /**
     * API: 用户注册接口
     * 路径: POST /api/user/register
     * 接收: JSON 格式的 User 对象（包含 username 和 password）
     * @param user 包含注册信息的 User 对象
     * @return 注册结果的 JSON 响应
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 简单的参数校验
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名或密码不能为空");
            return result;
        }

        // 2. 调用 Service 层进行注册逻辑处理
        try {
            boolean success = userService.registerUser(user);
            if (success) {
                result.put("success", true);
                result.put("message", "用户注册成功");
            } else {
                result.put("success", false);
                result.put("message", "注册失败：用户名已存在或数据库错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }
        return result;
    }
}