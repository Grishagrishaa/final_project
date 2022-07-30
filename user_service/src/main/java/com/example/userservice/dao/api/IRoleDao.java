package com.example.userservice.dao.api;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface IRoleDao extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
