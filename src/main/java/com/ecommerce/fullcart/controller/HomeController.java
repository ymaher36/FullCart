package com.ecommerce.fullcart.controller;

import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private UserService userService;

    @Autowired
    public HomeController(UserService service) {
        userService = service;
    }

    @GetMapping("/")
    public String index(Model theModel, Authentication auth) {
        try {
            User user = userService.findUserByUsername(auth.getName());
            theModel.addAttribute("user", user);
        } catch (Exception ignored) {
        }

        return "home";
    }

}
