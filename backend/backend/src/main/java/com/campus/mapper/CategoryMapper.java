package com.campus.mapper;

import com.campus.model.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper // 告诉 Spring Boot/MyBatis 这是一个 Mapper 接口
public interface CategoryMapper {

    /**
     * 插入新的分类数据
     * @param category 包含分类名称
     * @return 影响的行数
     */
    int insertCategory(Category category);

    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<Category> selectAllCategories();

    // ... 还可以添加 updateCategory 和 deleteCategory 方法
}