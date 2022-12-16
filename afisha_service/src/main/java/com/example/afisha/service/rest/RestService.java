package com.example.afisha.service.rest;

import com.example.afisha.dto.uuidTest.ConcertCategoryTest;
import com.example.afisha.dto.uuidTest.CountryTest;
import com.example.afisha.security.UserDetailsUser;
import com.example.afisha.security.UserHolder;
import com.example.afisha.security.utils.JwtTokenUtil;
import com.example.afisha.service.rest.api.IRestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Component
public class RestService implements IRestService {
    @Value("${app.country.url}")
    private String countryUrl;
    @Value("${app.concert-category.url}")
    private String concertCategoryUrl;

    private final WebClient webClient;
    private final UserHolder userHolder;

    public RestService(WebClient webClient, UserHolder userHolder) {
        this.webClient = webClient;
        this.userHolder = userHolder;
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
            throw new UsernameNotFoundException("USER NOT FOUND | INVALID TOKEN");
        }
        return user;
    }

    public boolean checkCountry (UUID id){
        try {
            webClient
                    .get()
                    .uri(String.join("/", countryUrl, id.toString()))
                    .header(AUTHORIZATION, ("Bearer ".concat(
                            JwtTokenUtil.generateAccessToken(userHolder.getUser().getUsername()))))

                    .retrieve().bodyToMono(CountryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            return false;
        }
        return true;
    }

    public boolean checkCategory(UUID id) {
        try{
            webClient
                    .get()
                    .uri(String.join("/", concertCategoryUrl, id.toString()))
                    .header(AUTHORIZATION, ("Bearer ".concat(
                            JwtTokenUtil.generateAccessToken(userHolder.getUser().getUsername()))))
                    .retrieve().bodyToMono(ConcertCategoryTest.class).block();//ЕСЛИ ЗАПИСЬ НЕ НАЙДЕНА -> Ловим ошибку
        }catch (WebClientResponseException e){
            return false;
        }
        return true;
    }
}
