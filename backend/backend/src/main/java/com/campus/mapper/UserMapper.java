package com.campus.mapper;

import com.campus.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // 根据用户名查找用户
    User findByUsername(String username);

    // 插入新用户
    int insertUser(User user);
    /**
     * 根据用户ID查询用户详情
     */
    User selectById(Integer id);
}