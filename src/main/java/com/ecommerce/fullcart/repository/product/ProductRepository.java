package com.ecommerce.fullcart.repository.product;

import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsBySeller(User seller);

}
