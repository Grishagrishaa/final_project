package com.example.afisha.security;

import com.example.afisha.exceptions.MyRoleNotFoundException;
import com.example.afisha.security.utils.ERole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleInfoNotFoundException;
import javax.management.relation.RoleNotFoundException;
import javax.naming.AuthenticationException;
import javax.naming.NamingException;


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
     *  true authorized
     */

    boolean isAuthenticated(){
        return !SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public ERole getAuthority(){
        if(!isAuthenticated()){
            return null;
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
