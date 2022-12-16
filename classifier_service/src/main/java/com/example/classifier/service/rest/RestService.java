package com.example.classifier.service.rest;

import com.example.classifier.security.UserDetailsUser;
import com.example.classifier.service.rest.api.IRestService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class RestService implements IRestService {
    private final WebClient webClient;

    public RestService(WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public UserDetails loadUser(String headerValue) throws UsernameNotFoundException {
        UserDetailsUser user = null;
        try {
            user = webClient
                    .get()
                    .header(AUTHORIZATION, headerValue)
                    .retrieve().bodyToMono(UserDetailsUser.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return user;
    }
}
