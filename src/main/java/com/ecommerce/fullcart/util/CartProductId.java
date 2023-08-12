package com.ecommerce.fullcart.util;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartProductId implements Serializable {
    @Column(name = "cart_id")
    private int cartId;
    @Column(name = "product_id")
    private int productId;

    public CartProductId() {
    }

    public CartProductId(int cartId, int productId) {
        this.cartId = cartId;
        this.productId = productId;
    }

    public int getCartId() {
        return cartId;
    }


    public int getProductId() {
        return productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProductId that = (CartProductId) o;
        return cartId == that.cartId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartId, productId);
    }
}
