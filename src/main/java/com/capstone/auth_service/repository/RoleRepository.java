package com.capstone.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.auth_service.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}