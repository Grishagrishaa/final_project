package com.example.userservice.config;

import com.example.userservice.converters.SaveUserDtoToUserConverter;
import com.example.userservice.converters.UserToUserDetailsConverter;
import com.example.userservice.dao.api.IRoleDao;
import com.example.userservice.dao.api.IUserDao;
import com.example.userservice.service.UserService;
import com.example.userservice.validation.SaveUserDtoPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UsersStorageConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(SaveUserDtoPredicate userDtoValidator,
                                                  SaveUserDtoToUserConverter toUserConverter,
                                                 ConversionService conversionService,
                                                 IUserDao userDao, IRoleDao roleDao) {

        return  new UserService(userDtoValidator, toUserConverter, conversionService, userDao, roleDao);
    }
}
