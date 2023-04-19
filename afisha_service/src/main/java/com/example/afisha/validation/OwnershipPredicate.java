package com.example.afisha.validation;

import com.example.afisha.security.UserHolder;
import com.example.afisha.security.enums.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

@Component
@RequiredArgsConstructor
public class OwnershipPredicate implements BiPredicate<String, String > {
    private final UserHolder userHolder;

    @Override
    public boolean test(String eventAuthor, String username) {
        if(!eventAuthor.equalsIgnoreCase(username) && !userHolder.getAuthority().equals(ERole.ADMIN)){
            throw new IllegalArgumentException("IT'S NOT YOUR EVENT, YOU CAN'T CHANGE IT");
        }

        return true;
    }
}
