package com.example.userservice.controllers;

import com.example.userservice.converters.UserPageToReadUsersDtoMyPage;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.ReadUserDto;
import com.example.userservice.dto.SaveUserDto;
import com.example.userservice.pagination.MyPage;
import com.example.userservice.service.api.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final IUserService service;
    private final ModelMapper mapper;
    private final Converter<Page<User>, MyPage<ReadUserDto>> pageConverter;

    public UserController(IUserService service, ModelMapper mapper, Converter<Page<User>, MyPage<ReadUserDto>> pageConverter) {
        this.service = service;
        this.mapper = mapper;
        this.pageConverter = pageConverter;
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<ReadUserDto> get(@PathVariable UUID uuid){
        return new ResponseEntity<>(mapper.map(service.get(uuid), ReadUserDto.class), HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MyPage<ReadUserDto>> getAll(@RequestParam(required = false, defaultValue = "0", name = "page") Integer page,
                                                      @RequestParam(required = false, defaultValue = "5", name = "size") Integer size){

        return new ResponseEntity<>(pageConverter.convert(service.getAll(PageRequest.of(page, size, Sort.by("nick")))),
                                    HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody SaveUserDto userDto){
        service.create(mapper.map(userDto, User.class));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@PathVariable UUID uuid, @PathVariable LocalDateTime dt_update,
                       @RequestBody SaveUserDto userDto){
        service.update(uuid, dt_update, userDto);
    }
}
