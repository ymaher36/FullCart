package com.ecommerce.fullcart.dto;

import com.ecommerce.fullcart.entity.product.Category;
import com.ecommerce.fullcart.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

public class ProductDTO {
    @Size(min = 10, max = 200, message = " must be between 6 and 200 character.")
    private String name;

    @NotNull(message = " is required")
    @Size(min = 20, message = " must be between above 20 characters.")
    private String description;

    private String image;


    @NotNull(message = " is required")
    private User seller;

    @NotNull(message = " is required")
    private Category category;

    public ProductDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", seller=" + seller +
                ", category=" + category +
                '}';
    }
}
