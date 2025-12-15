package com.campus.service;

import com.campus.model.User;

import java.util.Map;

public interface UserService {
    /**
     * 注册用户
     * @param user 用户实体
     * @return 注册是否成功 (true/false)
     */
    // 注册方法
    Map<String, Object> register(User user);

    /**
     * 用户登录方法
     * @param user 包含用户名和密码
     * @return 登录结果（成功/失败信息及用户信息）
     */
    Map<String, Object> login(User user);
}