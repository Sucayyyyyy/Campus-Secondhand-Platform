package com.campus.controller;

import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
/* * 核心修复：添加跨域注解。
 * 只有加了这个，前端 5173 才能访问 8080 的接口，否则会报 CORS 错误导致 No Data
 */
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class ProductController { // <--- 补齐了这里误删的类定义

    @Autowired
    private ProductService productService;

    /**
     * API: 发布商品
     */
    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Product product, HttpServletRequest request) {
        // 从拦截器设置的 request 属性中获取当前用户 ID
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");
        System.out.println("当前登录用户ID是：" + currentUserId);
        product.setSellerId(currentUserId);
        return productService.publishProduct(product);
    }

    /**
     * API: 获取单个商品详情
     */
    @GetMapping("/{id}")
    public Product getDetail(@PathVariable("id") Long id) {
        return productService.getById(id);
    }

    /**
     * API: 获取商品列表（支持分页和搜索）
     */
    @GetMapping("/list")
    public Map<String, Object> getList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword){
        return productService.getProductList(pageNum, pageSize, keyword);
    }

    /**
     * API: 更新商品
     */
    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(product);
    }

    /**
     * API: 删除商品
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        Integer currentSellerId = (Integer) request.getAttribute("currentUserId");
        if (currentSellerId == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户未登录，操作失败");
            return result;
        }
        return productService.deleteProduct(id, currentSellerId);
    }
}