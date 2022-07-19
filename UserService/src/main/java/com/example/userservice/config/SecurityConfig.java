package com.example.userservice.config;

import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.filter.JwtFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter filter;
    private static final String ADMIN_ENDPOINT = "/api/v1/users/**";
    private static final String[] PUBLIC_ENDPOINTS = {"/api/v1/users/login","/api/v1/users/registration"};
    private static final String AUTHENTICATED = "/api/v1/users/me";

    public SecurityConfig(JwtFilter filter) {
        this.filter = filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.csrf().disable().cors().disable();

        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
                        }
                )
                .and();

        http.authorizeRequests()
                .antMatchers().permitAll()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers(AUTHENTICATED).authenticated()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority(ERole.ADMIN.name())

                .anyRequest().authenticated();

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
