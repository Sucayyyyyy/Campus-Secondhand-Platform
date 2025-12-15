package com.campus.mapper;

import com.campus.model.User;

public interface UserMapper {
    // 根据用户名查找用户
    User findByUsername(String username);

    // 插入新用户
    int insertUser(User user);
}