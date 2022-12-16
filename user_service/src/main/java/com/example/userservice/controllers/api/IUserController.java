package com.example.userservice.controllers.api;

import com.example.userservice.controllers.pagination.MyPage;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.JwtResponse;
import com.example.userservice.dto.users.LoginDto;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserController {

    ResponseEntity<User> create(@RequestBody SaveUserDto dto);

    ResponseEntity<MyPage<User>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                  @RequestParam(required = false, defaultValue = "5", name = "size") Integer size);

    ResponseEntity<User> get(@PathVariable UUID uuid);

    ResponseEntity<User> update(@PathVariable UUID uuid, @PathVariable LocalDateTime dt_update, @RequestBody SaveUserDto userDto);

    ResponseEntity registration(@RequestBody SignDto dto);

    ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto);

    ResponseEntity<UserDetails> details(); 
}
