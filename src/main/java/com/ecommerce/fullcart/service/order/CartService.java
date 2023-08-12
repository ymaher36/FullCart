package com.ecommerce.fullcart.service.order;

import com.ecommerce.fullcart.entity.order.Cart;
import com.ecommerce.fullcart.repository.order.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
