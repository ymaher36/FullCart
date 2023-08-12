package com.ecommerce.fullcart.repository.order;

import com.ecommerce.fullcart.entity.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
