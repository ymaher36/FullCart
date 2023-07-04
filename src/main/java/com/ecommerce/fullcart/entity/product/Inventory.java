package com.ecommerce.fullcart.entity.product;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "available_quantity")
    private int avaQuantity;

    @Column(name = "sold_quantity")
    private int soldQuantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product refProduct;

    public Inventory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvaQuantity() {
        return avaQuantity;
    }

    public void setAvaQuantity(int avaQuantity) {
        this.avaQuantity = avaQuantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public Product getRefProduct() {
        return refProduct;
    }

    public void setRefProduct(Product refProduct) {
        this.refProduct = refProduct;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", avaQuantity=" + avaQuantity +
                ", soldQuantity=" + soldQuantity +
                ", refProduct=" + refProduct +
                '}';
    }
}
