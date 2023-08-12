package com.ecommerce.fullcart.entity.order;

import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import jakarta.persistence.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity_of_items")
    private int quantityOfItems;

    @Column(name = "total_price")
    private double totalPrice;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CartProduct> products;


    public void addProduct(Product product) {
        CartProduct cartProduct = new CartProduct(this, product);
        products.add(cartProduct);
        product.getCarts().add(cartProduct);
    }

    public void removeProduct(Product product) {
        for (Iterator<CartProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            CartProduct cartProduct = iterator.next();

            if (cartProduct.getCart().equals(this) &&
                    cartProduct.getProduct().equals(product)) {
                iterator.remove();
                cartProduct.getProduct().getCarts().remove(cartProduct);
                cartProduct.setCart(null);
                cartProduct.setProduct(null);
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantityOfItems() {
        return quantityOfItems;
    }

    public void setQuantityOfItems(int quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getProductAssoc() {
        return this.products;
    }

    public void setProductAssoc(List<CartProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id == cart.id && quantityOfItems == cart.quantityOfItems && Double.compare(cart.totalPrice, totalPrice) == 0 && Objects.equals(user, cart.user) && Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantityOfItems, totalPrice, user, products);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", quantityOfItems=" + quantityOfItems +
                ", totalPrice=" + totalPrice +
                ", user=" + user.getName() +
                ", productAssoc=" + products +
                '}';
    }
}
