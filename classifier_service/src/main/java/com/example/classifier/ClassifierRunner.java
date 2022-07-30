package com.example.classifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.example.classifier.dao.api")
@EnableTransactionManagement
public class ClassifierRunner {
    public static void main(String[] args) {
        SpringApplication.run(ClassifierRunner.class, args);
    }

}
