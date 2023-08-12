package com.ecommerce.fullcart.repository.order;

import com.ecommerce.fullcart.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
