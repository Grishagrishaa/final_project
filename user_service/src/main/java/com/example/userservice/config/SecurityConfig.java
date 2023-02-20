package com.example.userservice.config;

import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.controllers.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtFilter filter;
    @Value("${app.users.url}/**")
    private String ADMIN_ENDPOINT; /* = userEndpoint + "/**";*/
    @Value("#{'${app.users.url}/login,${app.users.url}/registration'.split(',')}")
    private List<String> PUBLIC_ENDPOINTS; /*= { userEndpoint + "/login", userEndpoint + "/registration" }*/;
    @Value("${app.users.url}/me")
    private String AUTHENTICATED; /* = userEndpoint + "/me";*/

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
                .antMatchers(PUBLIC_ENDPOINTS.toArray(new String[PUBLIC_ENDPOINTS.size()])).permitAll()
                .antMatchers(AUTHENTICATED).authenticated()
                .antMatchers(ADMIN_ENDPOINT).hasAuthority(ERole.ADMIN.name())

                .anyRequest().authenticated();

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );
    }
}
