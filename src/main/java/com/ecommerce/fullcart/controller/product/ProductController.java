package com.ecommerce.fullcart.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/search")
    public void productSearch(@RequestParam(required = true) String search) {
        System.out.println(search);

    }
}
