package com.example.afisha.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class UserHolder {

    public UserDetails getUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  userDetails.getUsername();
    }

    /**
     * boolean isAuthenticated()
     * @return false if user - anonymous (did not authorize)
     *  true if not authorized
     */
    public boolean isAuthenticated(){
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    /**
     * boolean isAdmin()
     * @return false if user - hasn't admin authority or not authenticated at all
     *
     */
    public boolean isAdmin(){
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")){
            return false;
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }
}
