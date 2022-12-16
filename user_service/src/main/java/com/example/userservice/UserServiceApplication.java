package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories("com.example.userservice.dao.api")
//@Profile("dev")
@EnableWebMvc
@SpringBootApplication
public class UserServiceApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
