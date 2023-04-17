package com.example.userservice.config;

import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.controllers.filters.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtFilter filter;
    @Value("${app.users.url}/**")
    private String ADMIN_ENDPOINT; /* = userEndpoint + "/**";*/
    @Value("#{'${app.users.url}/login,${app.users.url}/registration'.split(',')}")
    private String[] PUBLIC_ENDPOINTS = {"/api/v1/users/login", "/api/v1/users/registration"};
    @Value("${app.users.url}/me")
    private String AUTHENTICATED_ENDPOINT; /* = userEndpoint + "/me";*/

    public SecurityConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable().cors().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .addFilterBefore(
                        filter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and()

                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_ENDPOINTS).anonymous()
                .requestMatchers(AUTHENTICATED_ENDPOINT).authenticated()
                .requestMatchers(ADMIN_ENDPOINT).hasAuthority(ERole.ADMIN.name())
                .anyRequest().anonymous()

                .and()

                .httpBasic(Customizer.withDefaults()) // (4)

                .build();
    }
}
