package com.example.userservice.validation;

import com.example.userservice.dto.ErrorMessage;
import com.example.userservice.dto.MyValidationException;
import com.example.userservice.dto.users.LoginDto;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.dto.users.SignDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SaveUserDtoPredicate implements Predicate<SaveUserDto> {
    private List<ErrorMessage> errorMessages;

    @Value("${app.minNickLength}")
    private Integer MIN_NICK_LENGTH;
    @Value("${app.maxNickLength}")
    private Integer MAX_NICK_LENGTH;
    @Value("${app.mailPattern}")
    private String MAIL_PATTERN;
    @Value("${app.minPasswordLength}")
    private Integer MIN_PASSWORD_LENGTH;
    @Value("${app.maxPasswordLength}")
    private Integer MAX_PASSWORD_LENGTH;


    @Override
    public boolean test(SaveUserDto dto) {
        errorMessages = new ArrayList<>();

        checkNick(dto.getNick());
        checkMail(dto.getMail());
        checkPassword(dto.getPassword());

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }

    public boolean testUpdate(SaveUserDto dto) {
        errorMessages = new ArrayList<>();

        if(dto.getNick() != null) checkNick(dto.getNick());
        if(dto.getMail() != null) checkMail(dto.getMail());
        if(dto.getPassword() != null) checkPassword(dto.getPassword());

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }

    private void checkNick(String nick){
        if(nick == null || nick.length() < MIN_NICK_LENGTH || nick.length() > MAX_NICK_LENGTH){
            errorMessages.add(new ErrorMessage("NICK", "NICK MUST BE GREATER THAN " + MIN_NICK_LENGTH +
                    " AND LESS THAN " + MAX_NICK_LENGTH));
        }
    }

    private void checkMail(String mail){
        Pattern mailPattern = Pattern.compile(MAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher matcher = mailPattern.matcher(mail);
        if (!matcher.find()) {
            errorMessages.add(new ErrorMessage("MAIL", "INCORRECT MAIL FORMAT"));
        }
    }

    private void checkPassword(String password){
        if(password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH){
            errorMessages.add(new ErrorMessage("PASSWORD", "PASSWORD MUST BE GREATER THAN " + MIN_PASSWORD_LENGTH +
                    " AND LESS THAN " + MAX_PASSWORD_LENGTH));
        }
    }
}
