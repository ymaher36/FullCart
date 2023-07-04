package com.ecommerce.fullcart.entity.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "price")
public class Price {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "effective_date")
    @NotNull(message = " is required")
    @CreationTimestamp
    private Date effectiveDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @NotNull(message = " is required")
    private Product pricingProduct;

    public Price() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Product getPricingProduct() {
        return pricingProduct;
    }

    public void setPricingProduct(Product pricingProduct) {
        this.pricingProduct = pricingProduct;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", sellingPrice=" + sellingPrice +
                ", effectiveDate=" + effectiveDate +
                ", pricingProduct=" + pricingProduct +
                '}';
    }
}
