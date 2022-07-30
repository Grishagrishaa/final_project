package com.example.classifier.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class UserServiceImpl {
    private final WebClient WebClient;

    public UserServiceImpl(org.springframework.web.reactive.function.client.WebClient webClient) {
        WebClient = webClient;
    }

    public UserDetails loadUser(String headerValue) throws UsernameNotFoundException {
        UserDetailsUser user = null;
        try {
            user = WebClient
                    .get()
                    .uri("http://user-service:8080/users/me")
                    .header(AUTHORIZATION, headerValue)
                    .retrieve().bodyToMono(UserDetailsUser.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return user;
    }
}
