package com.capstone.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.auth_service.entity.RoleEntity;
import com.capstone.auth_service.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public RoleEntity getRoleById(int roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

}
