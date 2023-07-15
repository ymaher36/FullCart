package com.ecommerce.fullcart.controller.user;

import com.ecommerce.fullcart.dto.UserDto;
import com.ecommerce.fullcart.entity.user.Role;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.user.RoleService;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.hibernate.sql.ast.SqlTreeCreationLogger.LOGGER;

@Controller
@RequestMapping("/register")
public class RegistrationController {


    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model theModel, Authentication auth) {
        if (auth != null) return "redirect:/";
        Map<Integer, String> roleMap = new HashMap<>();
        for (Role role : roleService.findAllRoles()) {
            roleMap.put(role.getId(), role.getName());
        }
        theModel.addAttribute("newUser", new UserDto());
        theModel.addAttribute("roleMap", roleMap);


        return "auth/registration-form";
    }

    @PostMapping("/processRegistration")
    public String processRegistrationForm(
            @Valid @ModelAttribute("newUser") UserDto newUser,
            BindingResult theBindingResult,
            @RequestParam(name = "role") String roleId,
            Model theModel,
            HttpServletRequest request) {

        String username = newUser.getUsername();
        logger.info("Processing registration form for: " + username);
        // form validation

        if (theBindingResult.hasErrors()) {
            Map<Integer, String> roleMap = new HashMap<>();
            for (Role role : roleService.findAllRoles()) {
                roleMap.put(role.getId(), role.getName());
            }
            theModel.addAttribute("roleMap", roleMap);
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
        User savedUser = userService.save(newUser, roleId);
        logger.info("Successfully created user: " + username);

        // place user in the web http session for later use
//        session.setAttribute("user", savedUser);
        try {
            request.login(savedUser.getUsername(), newUser.getPassword());
        } catch (ServletException e) {
            LOGGER.error("Error while login ", e);
        }
        return "redirect:/";
    }
}
