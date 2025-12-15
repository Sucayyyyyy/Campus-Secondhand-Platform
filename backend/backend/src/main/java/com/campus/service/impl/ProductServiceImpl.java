package com.campus.service.impl;

import com.campus.mapper.ProductMapper;
import com.campus.model.Product;
import com.campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired // 自动注入 ProductMapper
    private ProductMapper productMapper;
    @Override
    public List<Product> getAvailableProducts() {
        return productMapper.selectAllAvailableProducts();
    }
}
