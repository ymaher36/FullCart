package com.ecommerce.fullcart.entity.order;

import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.Address;
import com.ecommerce.fullcart.entity.user.User;
import jakarta.persistence.*;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity_of_items")
    private int quantityOfItems;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "others")
    private String others;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "shipping_address_id")
    private Address address;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<OrderProduct> products;

    public void addProduct(Product product) {
        OrderProduct orderProduct = new OrderProduct(this, product);
        products.add(orderProduct);
        product.getOrders().add(orderProduct);
    }

    public void removeProduct(Product product) {
        for (Iterator<OrderProduct> iterator = products.iterator();
             iterator.hasNext(); ) {
            OrderProduct orderProduct = iterator.next();

            if (orderProduct.getOrder().equals(this) &&
                    orderProduct.getProduct().equals(product)) {
                iterator.remove();
                orderProduct.getProduct().getOrders().remove(orderProduct);
                orderProduct.setOrder(null);
                orderProduct.setProduct(null);
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderProduct> getProductAssoc() {
        return products;
    }

    public void setProductAssoc(List<OrderProduct> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && quantityOfItems == order.quantityOfItems && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(paymentType, order.paymentType) && Objects.equals(others, order.others) && Objects.equals(address, order.address) && Objects.equals(user, order.user) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantityOfItems, totalPrice, paymentType, others, address, user, products);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantityOfItems=" + quantityOfItems +
                ", totalPrice=" + totalPrice +
                ", paymentType='" + paymentType + '\'' +
                ", others='" + others + '\'' +
                ", address=" + address +
                ", user=" + user +
                ", productAssoc=" + products +
                '}';
    }
}
