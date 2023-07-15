package com.ecommerce.fullcart.controller;

import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public String index(Model theModel, HttpServletRequest request) throws Exception {

        if (request.getSession().getAttribute("userId") != null) {
            int userId = (int) request.getSession().getAttribute("userId");
            User user = userService.findUserById(userId);
            theModel.addAttribute("user", user);
        }


        return "home";
    }

}
