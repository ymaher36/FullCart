package com.ecommerce.fullcart.repository.user;

import com.ecommerce.fullcart.entity.user.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GovernorateRepository extends JpaRepository<Governorate, Integer> {
    Governorate findById(int id);
}
