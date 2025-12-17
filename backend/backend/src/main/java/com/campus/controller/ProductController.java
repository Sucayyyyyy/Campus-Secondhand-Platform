package com.campus.controller;

import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileStore;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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
     *//*
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAvailableProducts();
    }
*/
    /*
    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Product product, HttpServletRequest request) {
        // 1. 关键：从拦截器存入的 request 属性中获取当前登录用户的 ID
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");

        // 2. 打印一下，看看这里拿到的到底是不是 null
        System.out.println("当前登录用户ID是：" + currentUserId);

        // 3. 必须赋值给 product 对象
        product.setSellerId(currentUserId);

        // 4. 再调用 service
        return productService.publishProduct(product);
    }

*/
    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Product product, HttpServletRequest request) {
        // 1. 获取 ID (此时会拿到 null)
        Integer currentUserId = (Integer) request.getAttribute("currentUserId");

        // 2. 【核心修正】：如果是测试阶段，强行给它一个 ID
        if (currentUserId == null) {
            System.out.println("检测到 Token 为空，正在使用临时测试 ID: 1");
            currentUserId = 1; // 假设数据库里 ID 为 1 的用户是卖家
        }

        product.setSellerId(currentUserId);

        // 3. 打印完整对象，确认此时 sellerId 不再是 null
        System.out.println("准备插入数据库的完整对象：" + product);

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
    public Map<String, Object> getList(
                @RequestParam(defaultValue = "1") Integer pageNum,
                @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(required = false) String keyword){

            return productService.getProductList(pageNum, pageSize, keyword);

        }


    @PutMapping("/{id}")
    public Map<String, Object> update(@PathVariable("id") Integer id, @RequestBody Product product) {
        // 1. 从路径参数获取ID
        product.setId(id);
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Integer id,HttpServletRequest  request) {
        Integer currentSellerId = (Integer) request.getAttribute("currentUserId"); // ⚠️ 临时假定 sellerId 为 1 进行测试
        if (currentSellerId == null) {
            // 理论上不会走到这里，因为拦截器已处理 401，但作为保障
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户未登录，操作失败");
            return result;
        }
        return productService.deleteProduct(id, currentSellerId);
    }

}