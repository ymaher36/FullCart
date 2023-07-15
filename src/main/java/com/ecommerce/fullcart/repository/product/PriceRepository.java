package com.ecommerce.fullcart.repository.product;

import com.ecommerce.fullcart.entity.product.Price;
import com.ecommerce.fullcart.entity.product.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Integer> {
    Price findTopByPricingProduct_IdOrderByEffectiveDateDesc(int pricingProductId);
}
