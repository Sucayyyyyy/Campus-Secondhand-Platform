package com.campus.service.impl;

import com.campus.mapper.CategoryMapper; // 引入 Mapper
import com.campus.model.Category;
import com.campus.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper; // 🚨 注入 Mapper

    @Override
    public Category addCategory(Category category) {
        // 调用 Mapper 插入数据，useGeneratedKeys 会把 ID 填充回 category 对象
        int rows = categoryMapper.insertCategory(category);
        if (rows > 0) {
            return category; // 返回包含自增 ID 的对象
        }
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.selectAllCategories(); // 调用 Mapper 查询
    }
}