package com.example.classifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories("com.example.classifier.dao.api")
@EnableTransactionManagement
@EnableSwagger2WebMvc
//@Profile("dev")
public class ClassifierRunner {
    public static void main(String[] args) {
        SpringApplication.run(ClassifierRunner.class, args);
    }

}
