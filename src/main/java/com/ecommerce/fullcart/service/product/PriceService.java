package com.ecommerce.fullcart.service.product;

import com.ecommerce.fullcart.entity.product.Price;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.repository.product.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PriceService {
    private PriceRepository priceRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price findLatestPriceOfProduct(int productId) {
        return priceRepository.findTopByPricingProduct_IdOrderByEffectiveDateDesc(productId);
    }

    public Price save(Product product, double price) {
        Price newPrice = new Price();
        newPrice.setSellingPrice(price);
        newPrice.setPricingProduct(product);
        newPrice.setEffectiveDate(new Date());
        return priceRepository.save(newPrice);

    }
}
