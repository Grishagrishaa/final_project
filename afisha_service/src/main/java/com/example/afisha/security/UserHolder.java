package com.example.afisha.security;

import com.example.afisha.exceptions.MyRoleNotFoundException;
import com.example.afisha.security.enums.ERole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class UserHolder {

    public UserDetails getUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

//    public String getUsername() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return  userDetails.getUsername();
//    }

    public static String getUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  userDetails.getUsername();
    }
    /**
     * boolean isAuthenticated()
     * @return false if user - anonymous (did not authorize)
     *  true authorized
     */

    private boolean isAuthenticated(){
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public ERole getAuthority(){
        if(!isAuthenticated()){//todo contains
            return ERole.DEF;
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            return ERole.ADMIN;
        }
        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("USER"))){
            return ERole.USER;
        }

        throw new MyRoleNotFoundException("INTERNAL SERVER ERROR | CANNOT RECOGNIZE ROLE ");
    }
}
