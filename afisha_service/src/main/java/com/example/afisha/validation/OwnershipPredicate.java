package com.example.afisha.validation;

import com.example.afisha.security.UserHolder;
import com.example.afisha.security.enums.ERole;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
public class OwnershipPredicate implements BiPredicate<String, String > {
    private final UserHolder userHolder;

    public OwnershipPredicate(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Override
    public boolean test(String eventAuthor, String username) {
        if(!eventAuthor.equalsIgnoreCase(username) && !userHolder.getAuthority().equals(ERole.ADMIN)){
            throw new IllegalArgumentException("IT'S NOT YOUR EVENT, YOU CAN'T CHANGE IT");
        }

        return true;
    }
}
