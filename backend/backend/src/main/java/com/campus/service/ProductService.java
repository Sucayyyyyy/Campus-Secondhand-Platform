package com.campus.service;

import com.campus.model.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 获取所有在售商品列表
     * @return 在售商品列表
     */
    List<Product> getAvailableProducts();

    /**
     * 发布新商品
     * @param product 包含商品详情的对象
     * @return 包含 success 状态和 message 的 Map
     */
    Map<String, Object> publishProduct(Product product);
    /**
     * 获取单个商品详情
     * @param productId 商品ID
     * @return 包含商品数据或错误信息的 Map
     */
    Map<String, Object> getProductDetail(Integer productId);
}