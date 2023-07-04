package com.ecommerce.fullcart.service.product;

import com.ecommerce.fullcart.dto.ProductDTO;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        productRepository = repository;
    }

    public Product add(ProductDTO productDTO) {
        Product product = new Product();
        return product;
    }
}
