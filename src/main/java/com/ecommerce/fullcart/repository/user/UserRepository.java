package com.ecommerce.fullcart.repository.user;

import com.ecommerce.fullcart.entity.user.Role;
import com.ecommerce.fullcart.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUsersByRole(Role role);

    User findByUsername(String username);

    User findByUsernameAndIdNot(String username, int id);

    User findUserByPhoneNumberAndIdNot(String phoneNumber, int id);

    User findUserByEmailAndIdNot(String email, int id);
}
