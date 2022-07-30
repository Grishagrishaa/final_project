package com.example.userservice.service.api;

import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.users.SaveUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IUserService extends UserDetailsService {
    /**
     *
     * @param uuid  user id
     * @return one user
     */
    User get(UUID uuid);

    /**
     *
     * @param pageable - page parameters
     * @return page of users
     */
    Page<User> loadAll(Pageable pageable);

    /**
     *
     * @param user  user provided for saving in db
     */
    void createUser(SaveUserDto user);

    /**
     *
     * @param uuid  user id
     * @param updateDate  date, when user was updated last time
     * @param userDto  dto, provided with fields needed to update
     */
    void updateUser(UUID uuid, LocalDateTime updateDate, SaveUserDto userDto);

    /**
     *
     * @param nick the username identifying the user whose data is required.
     * @return  fully populated user record (never null)
     */
    UserDetails loadUserByUsername(String nick);
}
