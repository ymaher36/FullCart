package com.ecommerce.fullcart.controller.order;

import com.ecommerce.fullcart.entity.order.Cart;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.order.CartService;
import com.ecommerce.fullcart.service.product.ProductService;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/order")
public class OrderController {

    private UserService userService;
    private ProductService productService;
    private CartService cartService;

    @Autowired
    public OrderController(UserService userService, ProductService productService, CartService cartService) {
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @PostMapping("/addCart/{id}")
    @PreAuthorize("hasAuthority('Customer')")
    public String addCart(@PathVariable("id") int userId,
                          HttpServletRequest request,
                          @RequestParam("productId") int productId,
                          @RequestParam("quantity") int quantity,
                          RedirectAttributes redirectAttributes) throws Exception {
        if (userId != (int) request.getSession().getAttribute("userId")) {
            return "auth/access-denied";
        }
        User user = userService.findUserById(userId);
        Product product = productService.findById(productId);
        Cart cart = user.getCart();
        cart.addProduct(product);
        System.out.println(cart);
        System.out.println(product);
        cartService.save(cart);

        return "";
    }
}
