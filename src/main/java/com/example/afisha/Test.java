package com.example.afisha;

import com.example.afisha.dto.CountryTest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class Test {
    private final WebClient webClient;

    public Test(WebClient webClient) {
        this.webClient = webClient;
    }

    public void checkCountry(UUID id) {

        System.out.println(webClient
                .get()
                .uri(String.join("", "/country/", id.toString()))
                .retrieve().bodyToMono(CountryTest.class).block());
    }
}
