package com.example.userservice.controllers;

import com.example.userservice.converters.SignDtoToSaveDtoConverter;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.JwtResponse;
import com.example.userservice.dto.users.LoginDto;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import com.example.userservice.pagination.MyPage;
import com.example.userservice.service.UserHolder;
import com.example.userservice.service.UserService;
import com.example.userservice.utils.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final ModelMapper mapper;
    private final Converter<SignDto, SaveUserDto> toSaveDto;
    private final UserService service;
    private final UserHolder holder;
    private final PasswordEncoder encoder;

    public UserController(ModelMapper mapper, Converter<SignDto, SaveUserDto> toSaveDto,
                          UserService service,
                          UserHolder holder,
                          PasswordEncoder encoder) {
        this.mapper = mapper;
        this.toSaveDto = toSaveDto;
        this.service = service;
        this.holder = holder;
        this.encoder = encoder;
    }
    @PostMapping()
    @ResponseStatus(CREATED)
    private void create(@RequestBody SaveUserDto dto){
        service.createUser(dto);
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                 @RequestParam(required = false, defaultValue = "5", name = "size") Integer size){
        return new ResponseEntity<>(mapper.map(
                service.loadAll(PageRequest.of(page, size, Sort.by("createDate").descending())),
                MyPage.class),
                OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<User> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(service.get(uuid), OK);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(OK)
    public void update(@PathVariable UUID uuid, @PathVariable LocalDateTime dt_update, @RequestBody SaveUserDto userDto){
        service.updateUser(uuid, dt_update, userDto);
    }




    @PostMapping("/registration")
    @ResponseStatus(CREATED)
    public void registration(@RequestBody SignDto dto){
        service.createUser(toSaveDto.convert(dto));
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
    public UserDetails details(){
        return holder.getUser();
    }

}
