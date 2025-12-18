package com.campus.service;

import com.campus.model.Product;
import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 获取所有在售商品列表
     * @return 在售商品列表
     */
    /*
    List<Product> getAvailableProducts();
*/
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
    /**
     获取商品列表（支持分页和搜索）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @return 包含商品列表和分页信息的 Map
     */
    Map<String, Object> getProductList(Integer pageNum, Integer pageSize, String keyword);
    /**
     * 修改商品信息
     * @param product 包含ID、sellerId和要更新字段的对象
     * @return 包含 success 状态和 message 的 Map
     */
    Map<String, Object> updateProduct(Product product);

    /**
     * 逻辑删除/下架商品
     * @param productId 商品ID
     * @param sellerId 执行操作的卖家ID
     * @return 包含 success 状态和 message 的 Map
     */
    Map<String, Object> deleteProduct(Integer productId, Integer sellerId);

    /**
     * 获取用户发布的商品列表（支持分页和搜索）
     */
    Map<String, Object> getProductsBySellerId(Integer sellerId, Integer pageNum, Integer pageSize, String keyword);

    Product getById(Long id);
}