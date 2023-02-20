package com.example.userservice.config;

import com.example.userservice.converters.SaveUserDtoToUserConverter;
import com.example.userservice.converters.SignDtoToSaveUserDtoConverter;
import com.example.userservice.converters.SpringPageToMyPageConverter;
import com.example.userservice.converters.UserToUserDetailsConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new SaveUserDtoToUserConverter());
//        registry.addConverter(new SignDtoToSaveUserDtoConverter());
//        registry.addConverter(new SpringPageToMyPageConverter());
//        registry.addConverter(new UserToUserDetailsConverter());
//
//    }
}