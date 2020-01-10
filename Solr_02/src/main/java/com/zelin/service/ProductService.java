package com.zelin.service;

import com.zelin.pojo.Product;
import com.zelin.pojo.ProductVo;

import java.util.List;

public interface ProductService {
    List<Product> findByProductVo(ProductVo productVo);
}
