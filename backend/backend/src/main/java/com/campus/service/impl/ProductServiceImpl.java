package com.campus.service.impl;

import com.campus.mapper.ProductMapper;
import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired // 自动注入 ProductMapper
    private ProductMapper productMapper;

    @Override
    public List<Product> getAvailableProducts() {
        return productMapper.selectAllAvailableProducts();
    }

    @Override
    public Map<String, Object> publishProduct(Product product) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验 (基本字段非空检查)
        if (product.getName() == null || product.getPrice() == null || product.getSellerId() == null) {
            result.put("success", false);
            result.put("message", "商品名称、价格和卖家ID不能为空");
            return result;
        }

        // 2. 补充默认字段
        product.setStatus(1); // 默认设置为“在售”
        product.setPublishTime(new Date()); // 设置当前的发布时间

        // 3. 调用 Mapper 插入数据库
        try {
            int rows = productMapper.insertProduct(product);

            if (rows > 0) {
                result.put("success", true);
                result.put("message", "商品发布成功");
                // 可以返回新商品的ID
                result.put("productId", product.getId());
            } else {
                result.put("success", false);
                result.put("message", "数据库插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误，请检查数据库连接和SQL");
        }

        return result;
    }

}
