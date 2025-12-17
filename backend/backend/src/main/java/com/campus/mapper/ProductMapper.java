package com.campus.mapper;

import com.campus.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import java.util.List;

/**
 * 商品数据访问接口
 */@Mapper
public interface ProductMapper {

    /**
     * 查询所有在售商品列表
     * @return 商品列表
     */
    List<Product> selectAllAvailableProducts(Map<String, Object> params);
    int insertProduct(Product product);

    /**
     * 根据商品ID查询商品详情
     * @param id 商品ID
     * @return 对应的 Product 对象
     */
    Product selectById(Integer id);

    /**
     * 根据ID更新商品信息
     * @param product 包含ID和要更新字段的对象
     * @return 影响的行数 (通常为 1)
     */
    int updateProduct(Product product);
    /**
     * 逻辑删除商品（设置为下架状态 status=0）
     * @param productId 商品ID
     * @param sellerId 卖家ID (用于权限校验)
     * @return 影响的行数 (通常为 1)
     */
    int deleteProduct(@Param("id") Integer productId, @Param("sellerId") Integer sellerId);

 /**
            * 查询特定用户发布的商品列表（支持分页）
            * @param params 必须包含 sellerId，也可以包含 keyword
     * @return Product 对象列表
     */
    List<Product> selectProductsBySellerId(Map<String, Object> params);

    /**
     * 插入商品数据
     * @param product 商品对象
     * @return 影响的行数
     */
    int insert(Product product);


}