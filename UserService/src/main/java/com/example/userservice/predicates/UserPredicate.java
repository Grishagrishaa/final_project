package com.example.userservice.predicates;

import com.example.userservice.dao.api.IUserDao;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.ErrorMessage;
import com.example.userservice.exceptions.MyValidationException;
import com.example.userservice.service.api.IUserService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserPredicate implements Predicate<User> {
    private List<ErrorMessage> errorMessages;
    private final Environment env;
    private final IUserDao dao;

    public UserPredicate(Environment env, IUserDao dao) {
        this.env = env;
        this.dao = dao;
    }

    @Override
    public boolean test(User user) {
        errorMessages = new ArrayList<>();

        checkMail(user.getMail());
        checkNick(user.getNick());
        checkPassword(user.getPassword());

        if(!errorMessages.isEmpty()){
            throw new MyValidationException(errorMessages);
        }

        return true;
    }

    private void checkMail(String mail) {
        Pattern mailPattern = Pattern.compile(env.getProperty("mailPattern"), Pattern.CASE_INSENSITIVE);

        Matcher matcher = mailPattern.matcher(mail);
        if (mail == null || !matcher.find()) {
            errorMessages.add(new ErrorMessage("MAIL", "INCORRECT MAIL FORMAT"));
            return;
        }

        if(dao.findByMail(mail) != null){
            errorMessages.add(new ErrorMessage("MAIL", "MAIL ALREADY TAKEN"));
        }
    }

    private void checkNick(String nick){

        int minNickLength = Integer.parseInt(env.getProperty("minNickLength"));
        int maxNickLength = Integer.parseInt(env.getProperty("maxNickLength"));

        if(nick == null || nick.length() < minNickLength || nick.length() > maxNickLength){
            errorMessages.add(new ErrorMessage("NICK", "NICK MUST BE GREATER THAN " + minNickLength +
                    " AND LESS THAN " + maxNickLength));
            return;
        }

        if(dao.findByNick(nick) != null){
            errorMessages.add(new ErrorMessage("NICK", "NICK ALREADY TAKEN"));
        }
    }

    public void checkPassword(String password){
        int minPasswordLength = Integer.parseInt(env.getProperty("minPasswordLength"));
        int maxPasswordLength = Integer.parseInt(env.getProperty("maxPasswordLength"));

        if(password == null || password.length() < minPasswordLength || password.length() > maxPasswordLength){
            errorMessages.add(new ErrorMessage("PASSWORD", "PASSWORD MUST BE GREATER THAN " + minPasswordLength +
                    " AND LESS THAN " + maxPasswordLength));
        }
    }

}
