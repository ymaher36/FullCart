package com.ecommerce.fullcart.controller.user;

import com.ecommerce.fullcart.dto.AddressDto;
import com.ecommerce.fullcart.dto.UserDto;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.*;
import com.ecommerce.fullcart.service.user.AddressService;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;
    private AddressService addressService;

    @Autowired
    public ProfileController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
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
        UserDto userDto = new UserDto(user.getUsername(), user.getName(), user.getEmail(), user.getPhoneNumber());
        userDto.setPassword("00000000");
        theModel.addAttribute("editedUser", userDto);
        theModel.addAttribute("user", user);
        theModel.addAttribute("type", type);
        if (type.equals("address") || type.equals("addAddress")) {
            Map<Integer, String> governorateMap = new HashMap<>();
            for (Governorate governorate : addressService.findAllGovernorates()) {
                governorateMap.put(governorate.getId(), governorate.getName());
            }
            theModel.addAttribute("newAddress", new AddressDto());
            theModel.addAttribute("governorateMap", governorateMap);
        }
        if (user.getRole().getName().equals("Seller")) {
            return "seller/profile";
        } else {
            return "customer/profile";
        }


    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('Seller','Customer')")
    public String editProfile(@Valid @ModelAttribute("editedUser") UserDto userDto,
                              BindingResult bindingResult,
                              HttpServletRequest request,
                              @PathVariable("id") int userId,
                              RedirectAttributes redirectAttributes,
                              Model theModel) throws Exception {
        if (userId != (int) request.getSession().getAttribute("userId")) {
            return "auth/access-denied";
        }
        if (userService.checkEmailExists(userDto.getEmail(), userId) != null) {
            bindingResult.rejectValue("email", "error.editedUser", "An account already exists for this email");
        }
        if (userService.checkUsernameExists(userDto.getUsername(), userId) != null) {
            bindingResult.rejectValue("username", "error.editedUser", "An account already exists for this username");
        }
        if (userService.checkPhoneNumberExists(userDto.getPhoneNumber(), userId) != null) {
            bindingResult.rejectValue("phoneNumber", "error.editedUser", "An account already exists for this phone number");
        }
        User user = userService.findUserById(userId);
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("editedUser", userDto);
            theModel.addAttribute("user", user);
            theModel.addAttribute("type", "edit");
            if (user.getRole().getName().equals("Seller")) {
                return "seller/profile";
            } else {
                return "customer/profile";
            }
        }
        user = userService.edit(user, userDto);

        redirectAttributes.addAttribute("type", "details");
        return "redirect:/profile/" + userId;
    }

    @PostMapping("/addAddress/{id}")
    @PreAuthorize("hasAnyAuthority('Customer')")
    public String addAddress(@Valid @ModelAttribute("newAddress") AddressDto addressDto,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             @PathVariable("id") int userId,
                             RedirectAttributes redirectAttributes,
                             Model theModel) throws Exception {
        if (userId != (int) request.getSession().getAttribute("userId")) {
            return "auth/access-denied";
        }
        User user = userService.findUserById(userId);
        if (bindingResult.hasErrors()) {
            theModel.addAttribute("newAddress", addressDto);
            theModel.addAttribute("user", user);
            theModel.addAttribute("type", "addAddress");
            Map<Integer, String> governorateMap = new HashMap<>();
            for (Governorate governorate : addressService.findAllGovernorates()) {
                governorateMap.put(governorate.getId(), governorate.getName());
            }
            theModel.addAttribute("governorateMap", governorateMap);
            return "customer/profile";
        }
        Address address = addressService.save(addressDto, user);
        redirectAttributes.addAttribute("type", "address");
        return "redirect:/profile/" + userId;

    }

    @GetMapping("/districts")
    @ResponseBody
    public Map<Integer, String> districtSearch(@RequestParam(name = "id") String id) {
        Map<Integer, String> districtMap = new HashMap<>();
        for (District district : addressService.findDistrictsByGovernorate(Integer.parseInt(id))) {
            districtMap.put(district.getId(), district.getNameEng());
        }
        return districtMap;
    }
}
