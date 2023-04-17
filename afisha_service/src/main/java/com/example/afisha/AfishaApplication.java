package com.example.afisha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("com.example.afisha.dao.api")
@EnableJpaAuditing
@EnableCaching
@EnableTransactionManagement
@SpringBootApplication
public class AfishaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AfishaApplication.class, args);
    }

}
