package com.campus.controller;

import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController// @Controller + @ResponseBody (所有方法返回值自动转换为 JSON)
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * API: 获取在售商品列表
     * 路径: GET /api/products
     * @return 包含商品列表的 JSON 响应
     */
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAvailableProducts();
    }
}
