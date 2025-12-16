package com.campus.mapper;

import com.campus.model.Product;
import java.util.List;

/**
 * 商品数据访问接口
 */
public interface ProductMapper {

    /**
     * 查询所有在售商品列表
     * @return 商品列表
     */
    List<Product> selectAllAvailableProducts();
    int insertProduct(Product product);

    /**
     * 根据商品ID查询商品详情
     * @param id 商品ID
     * @return 对应的 Product 对象
     */
    Product selectById(Integer id);

}