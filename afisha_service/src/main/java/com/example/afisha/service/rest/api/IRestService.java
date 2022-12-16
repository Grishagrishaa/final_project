package com.example.afisha.service.rest.api;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IRestService {
    UserDetails loadUser(String headerValue);
    boolean checkCountry (UUID id);
    boolean checkCategory (UUID id);
}
