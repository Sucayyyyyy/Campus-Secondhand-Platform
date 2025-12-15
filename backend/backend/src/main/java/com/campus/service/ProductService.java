package com.campus.service;

import com.campus.model.Product;
import java.util.List;

public interface ProductService {

    /**
     * 获取所有在售商品列表
     * @return 在售商品列表
     */
    List<Product> getAvailableProducts();
}