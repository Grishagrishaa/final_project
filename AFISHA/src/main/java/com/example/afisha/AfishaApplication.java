package com.example.afisha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.example.afisha" })
@EnableJpaRepositories("com.example.afisha.dao.api")
public class AfishaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AfishaApplication.class, args);
    }

}
