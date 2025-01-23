package com.capstone.auth_service.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.capstone.auth_service.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

    String email;
    String passwordHash;
    SimpleGrantedAuthority role;

    public CustomUserDetails(UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.passwordHash = userEntity.getPasswordHash();
        this.role = new SimpleGrantedAuthority(userEntity.getRole().getRoleName());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role); // Return a list containing the single role
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
