package com.ecommerce.fullcart.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductController(){

    }

    @GetMapping("/search")
    public void productSearch(@RequestParam(required = true) String search) {
        System.out.println(search);

    }

    @GetMapping("/my-products")
    public String myProducts(Model theModel, @RequestParam(required = true) int user) {
        System.out.println(user);
        return "seller/my-products";
    }
}
