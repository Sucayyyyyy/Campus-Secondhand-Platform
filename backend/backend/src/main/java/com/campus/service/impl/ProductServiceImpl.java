package com.campus.service.impl;

import com.campus.mapper.ProductMapper;
import com.campus.model.Product;
import com.campus.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired // 自动注入 ProductMapper
    private ProductMapper productMapper;



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
    public Map<String, Object> getProductList(Integer pageNum, Integer pageSize, String keyword) {
        Map<String, Object> result = new HashMap<>();

        // 1. 设置默认分页参数
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;

        try {
            // 2. 启动 PageHelper 分页功能
            PageHelper.startPage(pageNum, pageSize);

            // 3. 构建查询参数 Map
            Map<String, Object> params = new HashMap<>();
            params.put("keyword", keyword);

            // 4. 调用 Mapper（PageHelper 会自动拦截并进行分页）
            List<Product> productList = productMapper.selectAllAvailableProducts(params);

            // 5. 使用 PageInfo 包装查询结果，获取详细的分页信息
            PageInfo<Product> pageInfo = new PageInfo<>(productList);

            // 6. 封装结果
            result.put("success", true);
            result.put("message", "商品列表查询成功");
            result.put("data", pageInfo.getList()); // 当前页数据
            result.put("total", pageInfo.getTotal()); // 总记录数
            result.put("pages", pageInfo.getPages()); // 总页数
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }

        return result;
    }

    @Override
    public Map<String, Object> updateProduct(Product product) {
        Map<String, Object> result = new HashMap<>();

        // 1. 关键参数校验：ID和卖家ID必须有
        if (product.getId() == null || product.getSellerId() == null) {
            result.put("success", false);
            result.put("message", "商品ID和卖家ID不能为空");
            return result;
        }

        try {
            // 2. 调用 Mapper 更新数据
            // 注意：Mapper 中的 SQL 已经通过 ID 和 sellerId 进行了双重校验
            int rows = productMapper.updateProduct(product);

            if (rows > 0) {
                result.put("success", true);
                result.put("message", "商品信息更新成功");
            } else {
                result.put("success", false);
                // 可能是商品ID不存在，或者 sellerId 不匹配（用户尝试修改别人的商品）
                result.put("message", "更新失败：商品不存在或您无权修改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }

        return result;
    }

    @Override
    public Map<String, Object> deleteProduct(Integer productId, Integer sellerId) {
        Map<String, Object> result = new HashMap<>();

        // 1. 关键参数校验
        if (productId == null || sellerId == null) {
            result.put("success", false);
            result.put("message", "商品ID和卖家ID不能为空");
            return result;
        }

        try {
            // 2. 调用 Mapper 执行逻辑删除（更新 status = 0）
            int rows = productMapper.deleteProduct(productId, sellerId);

            if (rows > 0) {
                result.put("success", true);
                result.put("message", "商品已成功下架/删除");
            } else {
                result.put("success", false);
                result.put("message", "下架失败：商品不存在或您无权操作");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }

        return result;
    }

    @Override
    public Map<String, Object> getProductsBySellerId(Integer sellerId, Integer pageNum, Integer pageSize, String keyword) {
        Map<String, Object> result = new HashMap<>();

        if (sellerId == null) {
            result.put("success", false);
            result.put("message", "用户ID不能为空");
            return result;
        }

        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;

        try {
            PageHelper.startPage(pageNum, pageSize);

            // 1. 构建查询参数 Map，必须包含 sellerId
            Map<String, Object> params = new HashMap<>();
            params.put("sellerId", sellerId);
            params.put("keyword", keyword);

            // 2. 调用 Mapper
            List<Product> productList = productMapper.selectProductsBySellerId(params);

            // 3. 包装结果
            PageInfo<Product> pageInfo = new PageInfo<>(productList);

            result.put("success", true);
            result.put("message", "用户商品列表查询成功");
            result.put("data", pageInfo.getList());
            result.put("total", pageInfo.getTotal());
            result.put("pages", pageInfo.getPages());
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("message", "服务器内部错误");
        }
        return result;
    }

}
