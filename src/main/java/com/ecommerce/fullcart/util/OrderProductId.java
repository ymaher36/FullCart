package com.ecommerce.fullcart.util;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class OrderProductId implements Serializable {
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "product_id")
    private int productId;

    public OrderProductId() {
    }

    public OrderProductId(int order, int orderProduct) {
        this.orderId = order;
        this.productId = orderProduct;
    }

    public int getOrder() {
        return orderId;
    }

    public void setOrder(int order) {
        this.orderId = order;
    }

    public int getProduct() {
        return productId;
    }

    public void setProduct(int product) {
        this.productId = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProductId that = (OrderProductId) o;
        return orderId == that.orderId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }
}
