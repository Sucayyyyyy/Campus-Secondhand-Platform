package com.campus.service;

import com.campus.model.User;

public interface UserService {
    /**
     * 注册用户
     * @param user 用户实体
     * @return 注册是否成功 (true/false)
     */
    boolean registerUser(User user);
}