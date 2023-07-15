package com.ecommerce.fullcart.service.product;

import com.ecommerce.fullcart.entity.product.Category;
import com.ecommerce.fullcart.repository.product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(int id) throws Exception {
        Category category;
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            category = categoryOptional.get();
        } else {
            throw new Exception("No Category with this Id");
        }
        return category;
    }
}
