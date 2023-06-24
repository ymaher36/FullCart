package com.ecommerce.fullcart.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name = "district")
public class District {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_ar")
    private String nameAr;

    @Column(name = "name_en")
    private String nameEng;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "governorate_id")
    private Governorate governorate;

    public District() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public Governorate getGovernorate() {
        return governorate;
    }

    public void setGovernorate(Governorate governorate) {
        this.governorate = governorate;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", nameAr='" + nameAr + '\'' +
                ", nameEng='" + nameEng + '\'' +
                ", governorate=" + governorate +
                '}';
    }
}
