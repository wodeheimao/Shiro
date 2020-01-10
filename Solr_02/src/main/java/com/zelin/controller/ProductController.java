package com.zelin.controller;


import com.zelin.pojo.Product;
import com.zelin.pojo.ProductVo;
import com.zelin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("list")
    public String productList(ProductVo productVo, Model model){
        
        return "product_list";
    }

    @RequestMapping("search")
    public String productSearch(ProductVo productVo, Model model){
        List<Product> products = productService.findByProductVo(productVo);
        model.addAttribute("products",products);
        model.addAttribute("vo",productVo);
        return "product_list";
    }
}
