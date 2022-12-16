package com.example.userservice.config;

import com.example.userservice.dao.api.IRoleDao;
import com.example.userservice.dao.api.IUserDao;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Predicate;

@Configuration
public class UsersStorageConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
                                                 ConversionService conversionService,
                                                 IUserDao userDao) {

        return  new UserService(conversionService, userDao);
    }
}
