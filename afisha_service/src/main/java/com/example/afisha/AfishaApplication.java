package com.example.afisha;

import com.example.afisha.dao.entity.BaseEvent;
import com.example.afisha.dao.entity.Film;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.example.afisha" })
@EnableJpaRepositories("com.example.afisha.dao.api")
@EnableJpaAuditing
@EnableTransactionManagement
public class AfishaApplication {
    public static void main(String[] args) {
        SpringApplication.run(AfishaApplication.class, args);
    }

}
