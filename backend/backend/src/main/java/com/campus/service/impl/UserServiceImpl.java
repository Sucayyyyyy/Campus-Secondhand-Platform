package com.campus.service.impl;

import com.campus.mapper.UserMapper;
import com.campus.model.User;
import com.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // 标记为 Spring 服务组件
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper; // 注入 DAO

    @Override
    public boolean registerUser(User user) {
        // 1. 检查用户名是否已存在
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return false; // 用户名已存在
        }

        // 2. (可选) 对密码进行加密处理 (此处简化，实际项目中必须加密)
        // user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. 插入新用户
        int result = userMapper.insertUser(user);
        return result > 0;
    }
}