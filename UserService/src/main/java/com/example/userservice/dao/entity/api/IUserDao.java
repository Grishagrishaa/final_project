package com.example.userservice.dao.entity.api;

import com.example.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface IUserDao extends JpaRepository<User, UUID> {
    Optional<User> findByNick(String nick);

}
