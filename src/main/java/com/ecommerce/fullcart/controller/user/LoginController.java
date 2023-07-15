package com.ecommerce.fullcart.controller.user;

import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/showLoginPage")
    public String showLoginPage(Authentication auth) {
        if (auth == null) return "auth/login-form";
        else return "redirect:/";
    }

    @GetMapping("/successLogin")
    public String successLogin(HttpServletRequest request, Authentication auth) {
        User user = userService.findUserByUsername(auth.getName());
        request.getSession().setAttribute("userId", user.getId());
        return "redirect:/";
    }

    /* ToDo: Using right way to save user id in session */
    /* ToDo: Removing user id for session when logged out */

    // add request mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "auth/access-denied";
    }

}










