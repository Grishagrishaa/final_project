package com.example.userservice.controllers;

import com.example.userservice.controllers.api.IUserController;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.JwtResponse;
import com.example.userservice.dto.users.LoginDto;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import com.example.userservice.controllers.pagination.MyPage;
import com.example.userservice.security.UserHolder;
import com.example.userservice.security.utils.JwtTokenUtil;
import com.example.userservice.service.api.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${app.users.url}")
@RequiredArgsConstructor
public class UserController implements IUserController {
    private final ConversionService conversionService;
    private final IUserService service;
    private final UserHolder holder;
    private final PasswordEncoder encoder;


    @PostMapping()
    public ResponseEntity<User> create(@RequestBody SaveUserDto dto){
        User user = service.createUser(dto);
        return new ResponseEntity<>(user, CREATED);
    }

    @GetMapping
    public ResponseEntity<MyPage<User>> getAll(@PageableDefault Pageable pageable){
        return new ResponseEntity<MyPage<User>>(conversionService.convert(service.findAll(pageable), MyPage.class), OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> findById(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.findById(uuid), OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<User> update(@PathVariable UUID uuid, @PathVariable LocalDateTime dt_update, @RequestBody SaveUserDto userDto){
        User user = service.updateUser(uuid, dt_update, userDto);
        return new ResponseEntity<>(user, OK);
    }




    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody SignDto dto){
        service.createUser(conversionService.convert(dto, SaveUserDto.class));
        return new ResponseEntity<>(CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto){
        UserDetails details = service.loadUserByUsername(loginDto.getNick());

        if(!encoder.matches(loginDto.getPassword(), details.getPassword())){
            throw new IllegalArgumentException("Пароль неверный");
        }

        return new ResponseEntity<>( JwtResponse.of(details.getUsername(), details.getAuthorities(),
                                     details.isEnabled(), JwtTokenUtil.generateAccessToken(details.getUsername())),
                                     OK );
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetails> details(){
        return new ResponseEntity<>(holder.getUser(), CREATED);
    }

}
