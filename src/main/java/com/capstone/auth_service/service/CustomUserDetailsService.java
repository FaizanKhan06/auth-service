package com.capstone.auth_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.capstone.auth_service.entity.UserEntity;
import com.capstone.auth_service.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);
        if (userEntity.isPresent()) {
            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity.get());
            return customUserDetails;
        } else {
            throw new UsernameNotFoundException(username + " not found");
        }
    }

}
