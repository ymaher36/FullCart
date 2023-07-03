package com.ecommerce.fullcart.repository.user;

import com.ecommerce.fullcart.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(int id);
}
