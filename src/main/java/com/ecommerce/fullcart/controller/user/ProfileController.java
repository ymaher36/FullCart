package com.ecommerce.fullcart.controller.user;

import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Seller','Customer')")
    public String UserProfile(@PathVariable("id") int userId,
                              @RequestParam("type") String type,
                              HttpServletRequest request,
                              Model theModel) throws Exception {
        if (userId != (int) request.getSession().getAttribute("userId")) {
            return "auth/access-denied";
        }

        User user = userService.findUserById(userId);
        theModel.addAttribute("user", user);
        theModel.addAttribute("type", type);
        if (user.getRole().getName().equals("Seller")) {
            return "seller/profile";
        } else {
            return "customer/profile";
        }


    }

}
