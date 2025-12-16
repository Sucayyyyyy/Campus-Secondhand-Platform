package com.campus.service.impl;

import com.campus.mapper.UserMapper;
import com.campus.model.User;
// ... 其他 import ...
import com.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // ... (register 方法代码保持不变) ...

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 检查用户名是否已存在
        // 注意：UserMapper 中需要有 findByUsername(String username) 方法
        User existingUser = userMapper.findByUsername(user.getUsername());

        // 如果用户已存在，返回失败
        if (existingUser != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        // 2. 插入新用户到数据库
        // 注意：UserMapper 中需要有 insertUser(User user) 方法
        try {
            int rows = userMapper.insertUser(user);

            if (rows > 0) {
                result.put("success", true);
                result.put("message", "用户注册成功");
            } else {
                result.put("success", false);
                result.put("message", "数据库插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "数据库操作异常");
        }

        return result;
    }
    /**
     * 用户登录实现
     */
    @Override
    public Map<String, Object> login(User user) {
        Map<String, Object> result = new HashMap<>();

        // 1. 根据用户名查找用户（复用 findByUsername 方法）
        User existingUser = userMapper.findByUsername(user.getUsername());

        if (existingUser == null) {
            result.put("success", false);
            result.put("msg", "用户不存在或用户名错误。");
            return result;
        }

        // 2. 校验密码
        if (existingUser.getPassword().equals(user.getPassword())) {

            // 登录成功：组装不包含密码的返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("id", existingUser.getId());
            data.put("username", existingUser.getUsername());
            data.put("phone", existingUser.getPhone());

            // 提示：高级项目中，这里通常还会生成并返回 JWT Token
            result.put("success", true);
            result.put("msg", "登录成功");
            result.put("data", data);
        } else {
            // 密码错误
            result.put("success", false);
            result.put("msg", "密码错误。");
        }

        return result;
    }

    @Override
    public Map<String, Object> getUserInfo(Integer userId) {
        Map<String, Object> result = new HashMap<>();

        if (userId == null) {
            result.put("success", false);
            result.put("message", "用户ID不能为空");
            return result;
        }

        User user = userMapper.selectById(userId);

        if (user != null) {
            result.put("success", true);
            result.put("message", "查询成功");


            user.setPassword(null); // 清除密码

            result.put("data", user);
        } else {
            result.put("success", false);
            result.put("message", "用户不存在");
        }
        return result;
    }
}