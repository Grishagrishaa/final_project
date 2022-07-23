package com.example.userservice.config;

import com.example.userservice.converters.SaveUserDtoToUserConverter;
import com.example.userservice.converters.UserToUserDetailsConverter;
import com.example.userservice.dao.entity.api.IRoleDao;
import com.example.userservice.dao.entity.api.IUserDao;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.validation.SaveUserDtoPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public UserDetailsService userDetailsService(SaveUserDtoPredicate userDtoValidator,
                                                 UserToUserDetailsConverter toUserDetailsConverter, SaveUserDtoToUserConverter toUserConverter,
                                                 IUserDao userDao, IRoleDao roleDao) {

        return  new UserService(userDtoValidator, toUserDetailsConverter, toUserConverter, userDao, roleDao);
    }
}
