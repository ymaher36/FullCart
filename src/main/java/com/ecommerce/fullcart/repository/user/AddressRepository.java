package com.ecommerce.fullcart.repository.user;

import com.ecommerce.fullcart.entity.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findById(int id);
}
