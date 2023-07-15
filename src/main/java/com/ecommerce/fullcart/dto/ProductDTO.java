package com.ecommerce.fullcart.dto;

import com.ecommerce.fullcart.entity.product.Category;
import com.ecommerce.fullcart.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

public class ProductDTO {

    private int id;
    @Size(min = 5, max = 200, message = " Must be between 6 and 200 character.")
    private String name;

    @NotNull(message = " is required")
    @Size(min = 10, message = " Must be between above 10 characters.")
    private String description;
    @NotNull
    @NotBlank(message = "must choose an image")
    private String image;


    private User seller;

    private Category category;
    @NotNull
    @Min(value = 1,message = "Must be more than or equal 1")
    private double price;
    @NotNull
    @Min(value = 1,message = "Must be more than or equal 1")
    private int quantity;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, @NotNull(message = " is required") String description, String image, Category category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", seller=" + seller +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
