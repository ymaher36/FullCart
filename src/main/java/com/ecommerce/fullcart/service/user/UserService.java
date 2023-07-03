package com.ecommerce.fullcart.service.user;

import com.ecommerce.fullcart.dto.UserDto;
import com.ecommerce.fullcart.entity.user.Role;
import com.ecommerce.fullcart.entity.user.User;

import com.ecommerce.fullcart.repository.user.RoleRepository;
import com.ecommerce.fullcart.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, RoleRepository roleRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else if (!user.getActive()) {
            try {
                throw new Exception("This user is disabled");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    // for multiple authorities approach
//    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
//        for (Role tempRole : roles) {
//            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
//            authorities.add(tempAuthority);
//        }
//
//        return authorities;
//    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(UserDto newUser) {
        User user = new User();
        Role role = roleRepository.findById(1);

        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setActive(true);
        user.setLastLogin(new Date());
        user.setCreatedAt(new Date());
        user.setRole(role);
        User savedUser = userRepository.save(user);
        return savedUser;
    }


}