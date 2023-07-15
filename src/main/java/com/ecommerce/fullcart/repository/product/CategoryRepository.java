package com.ecommerce.fullcart.repository.product;

import com.ecommerce.fullcart.entity.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
