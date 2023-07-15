package com.ecommerce.fullcart.service.product;

import com.ecommerce.fullcart.dto.ProductDTO;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository repository) {
        productRepository = repository;
    }

    public List<Product> findUserProducts(int id) {
        return productRepository.findProductsBySeller_Id(id);
    }

    public List<Product> findUserlatestProducts(int id) {
        return productRepository.findTop10BySeller_IdOrderByIdDesc(id);
    }

    public List<Product> findProductsByName(String name) {
        return productRepository.findProductsByNameContainsIgnoreCase(name);
    }

    public Product findById(int id) throws Exception {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product;
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            throw new Exception("No Product with this Id");
        }
        return product;
    }

    public void deleteByIdAndSellerId(int productId,int sellerId) {
        productRepository.deleteProductByIdAndSeller_Id(productId,sellerId);
    }

    public Product findByIdAndSellerId(int productId,int sellerId){
        return productRepository.findProductByIdAndSeller_Id(productId,sellerId);
    }
    public Product save(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        product.setSeller(productDTO.getSeller());
        product.setCategory(productDTO.getCategory());
        product.setDeleted(false);
        return productRepository.save(product);
    }
}
