package com.ecommerce.fullcart.repository.product;

import com.ecommerce.fullcart.entity.product.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    Inventory findInventoryByRefProduct_Id(int refProductId);
}
