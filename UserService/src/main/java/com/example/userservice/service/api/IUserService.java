package com.example.userservice.service.api;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.users.SaveUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    User get(UUID uuid);

    Page<User> loadAll(Pageable pageable);

    void createUser(SaveUserDto user);

    void updateUser(UUID uuid, LocalDateTime updateDate, SaveUserDto userDto);

    //UserDetails loadUserByUsername(String mail);
}
