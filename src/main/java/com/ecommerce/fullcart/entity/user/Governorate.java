package com.ecommerce.fullcart.entity.user;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "governorate")
public class Governorate {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "governorate",
            fetch = FetchType.LAZY)
    private List<District> districtList;

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

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    @Override
    public String toString() {
        return "Governorate { " +
                "id=" + id +
                ", name='" + name +
                " }";
    }
}
