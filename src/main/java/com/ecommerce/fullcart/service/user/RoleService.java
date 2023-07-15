package com.ecommerce.fullcart.service.user;

import com.ecommerce.fullcart.entity.user.Role;
import com.ecommerce.fullcart.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

}
