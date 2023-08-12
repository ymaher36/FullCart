package com.ecommerce.fullcart.repository.user;

import com.ecommerce.fullcart.entity.user.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    District findById(int id);

    List<District> findDistrictsByGovernorate_Id(int id);
}
