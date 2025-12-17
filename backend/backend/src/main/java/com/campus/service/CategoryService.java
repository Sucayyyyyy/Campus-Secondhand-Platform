package com.campus.service;

import com.campus.model.Category;
import java.util.List;

public interface CategoryService {

    /**
     * 添加新的商品分类
     * @param category 包含分类名称
     * @return 包含 ID 的新分类对象
     */
    Category addCategory(Category category);

    /**
     * 获取所有商品分类
     * @return 分类列表
     */
    List<Category> getAllCategories();

    // 完整的 CRUD 还需要 update 和 delete 方法...
}