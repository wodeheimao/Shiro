package com.zelin.controller;

import com.zelin.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("index")
    public String indexProduct(Model model){
        List<Product> productList = new ArrayList<Product>();
        Product p1 = new Product(1,"铅笔", "文具",10.2f, 12, "学习用品", "2009052610131660.jpg", "2019-12-12");
        Product p2 = new Product(2,"本","文具", 21.2f, 12, "学习用品", "2009052610131660.jpg", "2019-12-12");
        Product p3 = new Product(3,"尺子","文具", 10.2f, 12, "学习用品", "2009052610131660.jpg", "2019-12-12");
        productList.add(p1);
        productList.add(p2);
        productList.add(p3);
        model.addAttribute("productList",productList);
        return "index";
    }
}
