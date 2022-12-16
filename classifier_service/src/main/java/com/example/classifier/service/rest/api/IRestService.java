package com.example.classifier.service.rest.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface IRestService {
    UserDetails loadUser(String headerValue);
}
