package com.campus.model;

import java.io.Serializable;

public class Category implements Serializable {

    private Integer id;
    private String name;
    // ... 可以添加其他字段，如 createTime, updateTime 等

    // --- Getters and Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 建议：如果你使用的是 Lombok，可以使用 @Data 替代 Getters/Setters
}