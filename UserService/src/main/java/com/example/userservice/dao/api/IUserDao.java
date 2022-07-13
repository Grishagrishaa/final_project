package com.example.userservice.dao.api;

import com.example.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserDao extends JpaRepository<User, UUID> {
    User findByNick(String nick);

    User findByMail(String mail);

}
