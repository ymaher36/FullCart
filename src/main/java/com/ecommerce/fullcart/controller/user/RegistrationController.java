package com.ecommerce.fullcart.controller.user;

import com.ecommerce.fullcart.dto.UserDto;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {


    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model theModel, Authentication auth) {
        if (auth != null) return "redirect:/";

        theModel.addAttribute("newUser", new UserDto());

        return "auth/registration-form";
    }

    @PostMapping("/processRegistration")
    public String processRegistrationForm(
            @Valid @ModelAttribute("newUser") UserDto theNewUser,
            BindingResult theBindingResult,
            HttpSession session, Model theModel) {

        String username = theNewUser.getUsername();
        logger.info("Processing registration form for: " + username);

        // form validation
        if (theBindingResult.hasErrors()) {
            return "auth/registration-form";
        }

        // check the database if user already exists
        User existing = userService.findUserByUsername(username);
        if (existing != null) {
            theModel.addAttribute("newUser", new UserDto());
            theModel.addAttribute("registrationError", "Username already exists.");

            logger.warning("Username already exists.");
            return "auth/registration-form";
        }

        // create user account and store in the databse
        User savedUser = userService.save(theNewUser);
        logger.info("Successfully created user: " + username);

        // place user in the web http session for later use
        session.setAttribute("user", savedUser);

        return "home";
    }
}
