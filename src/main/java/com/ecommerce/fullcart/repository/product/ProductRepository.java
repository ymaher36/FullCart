package com.ecommerce.fullcart.repository.product;

import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findProductsBySeller_Id(int sellerId);
    List<Product> findTop10BySeller_IdOrderByIdDesc(int sellerId);

    List<Product> findProductsByNameContainsIgnoreCase(String name);

    Product findProductByIdAndSeller_Id(int id,int seller_id);
    void deleteProductByIdAndSeller_Id(int id, int seller_id);
}
