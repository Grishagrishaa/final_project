package com.example.userservice.service.api;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.SaveUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService {
    User get(UUID uuid);

    Page<User> getAll(Pageable pageable);

    void create(User user);

    void update(UUID uuid, LocalDateTime updateDate, SaveUserDto userCreateDto);

    Boolean checkNick(String nick);

    Boolean checkMail(String mail);
}
