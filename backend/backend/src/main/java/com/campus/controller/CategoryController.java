package com.campus.controller; // 确保包名正确

import com.campus.model.Category; // 假设你的 Category model 在这个包
import com.campus.service.CategoryService; // 假设你的 CategoryService 接口在这里
import com.campus.util.ResponseResult; // 假设你的统一返回体是 ResponseResult
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品分类管理
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService; // 确保你已经创建了 CategoryService 接口和实现类

    /**
     * POST /api/category
     * 创建新的商品分类
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        // 在这里调用 Service 层的方法来保存分类数据
        if (category.getName() == null || category.getName().isEmpty()) {
            return ResponseResult.error("分类名称不能为空");
        }

        // 假设 categoryService.addCategory(category) 返回包含ID的新分类对象
        Category newCategory = categoryService.addCategory(category);

        if (newCategory != null) {
            return ResponseResult.success("分类创建成功", newCategory);
        } else {
            return ResponseResult.error("分类创建失败");
        }
    }

    // 你还需要添加查询所有分类的方法，例如：
    /**
     * GET /api/category
     * 查询所有分类
     */
    @GetMapping("list")
    public ResponseResult getAllCategories() {
        return ResponseResult.success("查询成功", categoryService.getAllCategories());
    }

    // 完整的 CRUD 还需要 PUT (修改) 和 DELETE (删除) 方法...
}