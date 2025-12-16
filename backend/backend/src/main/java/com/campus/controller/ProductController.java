package com.campus.controller;

import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController// @Controller + @ResponseBody (所有方法返回值自动转换为 JSON)
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * API: 获取在售商品列表
     * 路径: GET /api/products
     *
     * @return 包含商品列表的 JSON 响应
     */
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAvailableProducts();
    }


    /**
     * API: 发布商品接口
     * 路径: POST /api/product/publish
     *
     * @param product 包含商品详情的对象
     * @return 包含发布结果的 Map
     */
    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Product product) {
        // Service 层已经做了部分校验，这里可以直接调用业务逻辑
        return productService.publishProduct(product);
    }

    /**
     * API: 获取单个商品详情接口
     * 路径: GET /api/product/{id}
     *
     * @param id 商品ID
     * @return 包含商品详情的 Map
     */
    @GetMapping("/{id}")
    public Map<String, Object> getDetail(@PathVariable("id") Integer id) {
        return productService.getProductDetail(id);

    }

    /**
     * API: 获取商品列表接口
     * 路径: GET /api/product/list
     *
     * @return 包含商品列表的 Map
     */
    @GetMapping("/list")
    public Map<String, Object> getList() {
        return productService.getProductList();
    }

    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        // 1. 从路径参数获取ID
        product.setId(id);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Integer id) {

        // 🚨 临时处理：这里依然是安全漏洞！
        // 必须从用户的 Session/Token 中获取当前登录用户的 ID
        Integer currentSellerId = 1; // ⚠️ 临时假定 sellerId 为 1 进行测试

        return productService.deleteProduct(id, currentSellerId);
    }

}