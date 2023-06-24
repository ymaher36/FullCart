package com.ecommerce.fullcart.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name = "governorate")
public class Governorate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public Governorate() {
    }

    public Governorate(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Governorate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
