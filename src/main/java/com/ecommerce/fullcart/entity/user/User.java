package com.ecommerce.fullcart.entity.user;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "last_login")
    private Date lastLogin;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "role_id")
    private Role roleId;
//    @OneToMany(mappedBy = )
}
