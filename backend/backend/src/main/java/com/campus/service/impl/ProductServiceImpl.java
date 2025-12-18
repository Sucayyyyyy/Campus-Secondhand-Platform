package com.campus.service.impl;

import com.campus.mapper.ProductMapper;
import com.campus.model.Product;
import com.campus.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> publishProduct(Product product) {
        Map<String, Object> result = new HashMap<>();

        // 1. 参数校验
        if (product.getName() == null || product.getPrice() == null || product.getSellerId() == null) {
            result.put("success", false);
            result.put("message", "商品名称、价格和卖家ID不能为空");
            return result;
        }

        // 2. 补充默认字段（这些逻辑要放在插入之前）
        product.setStatus(1); // 默认在售
        if (product.getPublishTime() == null) {
            product.setPublishTime(new Date()); // 设置当前时间
        }

        try {
            // 3. 执行插入 (统一使用我们在 Mapper 里定义的 insert 方法)
            int rows = productMapper.insert(product);

            if (rows > 0) {
                result.put("success", true);
                result.put("message", "商品发布成功");
                result.put("productId", product.getId()); // 返回自增ID
            } else {
                result.put("success", false);
                result.put("message", "数据库插入失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常以触发事务回滚
            throw new RuntimeException("发布商品失败: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Map<String, Object> getProductDetail(Integer productId) {
        Map<String, Object> result = new HashMap<>();
        if (productId == null || productId <= 0) {
            result.put("success", false);
            result.put("message", "商品ID无效");
            return result;
        }
        Product product = productMapper.selectById(productId);
        if (product != null) {
            result.put("success", true);
            result.put("data", product);
        } else {
            result.put("success", false);
            result.put("message", "未找到该商品");
        }
        return result;
    }

    @Override
    public Map<String, Object> getProductList(Integer pageNum, Integer pageSize, String keyword) {
        Map<String, Object> result = new HashMap<>();
        pageNum = (pageNum == null) ? 1 : pageNum;
        pageSize = (pageSize == null) ? 10 : pageSize;

        try {
            PageHelper.startPage(pageNum, pageSize);
            Map<String, Object> params = new HashMap<>();
            params.put("keyword", keyword);

            List<Product> productList = productMapper.selectAllAvailableProducts(params);
            PageInfo<Product> pageInfo = new PageInfo<>(productList);

            result.put("success", true);
            result.put("data", pageInfo.getList());
            result.put("total", pageInfo.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "查询列表失败");
        }
        return result;
    }

    @Override
    public Map<String, Object> updateProduct(Product product) {
        Map<String, Object> result = new HashMap<>();
        if (product.getId() == null || product.getSellerId() == null) {
            result.put("success", false);
            result.put("message", "商品ID和卖家ID不能为空");
            return result;
        }
        try {
            int rows = productMapper.updateProduct(product);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "更新成功" : "无权修改或商品不存在");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @Override
    public Map<String, Object> deleteProduct(Integer productId, Integer sellerId) {
        Map<String, Object> result = new HashMap<>();
        if (productId == null || sellerId == null) {
            result.put("success", false);
            result.put("message", "参数缺失");
            return result;
        }
        try {
            int rows = productMapper.deleteProduct(productId, sellerId);
            result.put("success", rows > 0);
            result.put("message", rows > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @Override
    public Map<String, Object> getProductsBySellerId(Integer sellerId, Integer pageNum, Integer pageSize, String keyword) {
        Map<String, Object> result = new HashMap<>();
        if (sellerId == null) {
            result.put("success", false);
            return result;
        }
        try {
            PageHelper.startPage(pageNum != null ? pageNum : 1, pageSize != null ? pageSize : 10);
            Map<String, Object> params = new HashMap<>();
            params.put("sellerId", sellerId);
            params.put("keyword", keyword);

            List<Product> productList = productMapper.selectProductsBySellerId(params);
            PageInfo<Product> pageInfo = new PageInfo<>(productList);

            result.put("success", true);
            result.put("data", pageInfo.getList());
            result.put("total", pageInfo.getTotal());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
        }
        return result;
    }

    @Override
    public Product getById(Long id) {
        return productMapper.selectDetailById(id);
    }
}