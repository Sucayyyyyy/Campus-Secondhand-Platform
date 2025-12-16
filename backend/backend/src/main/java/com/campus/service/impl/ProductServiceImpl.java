package com.campus.service.impl;

import com.campus.mapper.ProductMapper;
import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public Map<String, Object> getProductDetail(Integer productId) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验
        if (productId == null || productId <= 0) {
            result.put("success", false);
            result.put("message", "商品ID无效");
            return result;
        }

        // 2. 调用 Mapper 查询数据
        Product product = productMapper.selectById(productId);

        // 3. 封装结果
        if (product != null) {
            result.put("success", true);
            result.put("message", "查询成功");
            result.put("data", product); // 返回 Product 完整对象
        } else {
            result.put("success", false);
            result.put("message", "未找到该商品");
        }

        return result;
    }

    @Override
    public Map<String, Object> getProductList() {
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 调用 Mapper 查询所有在售商品
            List<Product> productList = productMapper.selectAllAvailableProducts();

            // 2. 封装结果
            if (productList != null && !productList.isEmpty()) {
                result.put("success", true);
                result.put("message", "商品列表查询成功");
                result.put("data", productList);
                result.put("total", productList.size());
            } else {
                result.put("success", true); // 列表为空也算成功
                result.put("message", "暂无在售商品");
                result.put("data", new ArrayList<>()); // 返回空列表，避免前端报错
                result.put("total", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }

        return result;
    }

}
