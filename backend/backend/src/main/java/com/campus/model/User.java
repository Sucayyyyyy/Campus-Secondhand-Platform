package com.campus.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    // 自动生成的唯一ID
    private Integer id;

    // 用户名（用于登录和查找）
    private String username;

    // 密码（实际项目中需要加密存储）
    private String password;

    // 联系方式/手机号（可选）
    private String phone;

    // 注册时间
    private Date createTime;

    // --- 构造函数 ---
    public User() {}

    // --- Getter 和 Setter 方法 ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='***'" + // 打印时隐藏密码
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}