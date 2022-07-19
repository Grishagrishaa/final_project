package com.example.userservice.service;

import com.example.userservice.converters.SaveUserDtoToUserConverter;
import com.example.userservice.converters.UserToUserDetailsConverter;
import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.api.IRoleDao;
import com.example.userservice.dao.entity.api.IUserDao;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserService implements UserDetailsService, IUserService {
    private final Predicate<SaveUserDto> userDtoValidator;
    private final UserToUserDetailsConverter toUserDetailsConverter;
    private final SaveUserDtoToUserConverter toUserConverter;
    private final IUserDao userDao;
    private final IRoleDao roleDao;


    public UserService(Predicate<SaveUserDto> userDtoValidator,
                       UserToUserDetailsConverter toUserDetailsConverter, SaveUserDtoToUserConverter toUserConverter,
                       IUserDao userDao, IRoleDao roleDao) {
        this.userDtoValidator = userDtoValidator;
        this.toUserDetailsConverter = toUserDetailsConverter;
        this.toUserConverter = toUserConverter;
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        return toUserDetailsConverter.convert(userDao.findByNick(nick));
    }

    @Override
    public User get(UUID uuid) {
        return userDao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
    }

    @Override
    public Page<User> loadAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override

    public void createUser(SaveUserDto userDto) {
        userDtoValidator.test(userDto);
        userDao.save(toUserConverter.convert(userDto));
    }



    @Override
    public void updateUser(UUID uuid, LocalDateTime updateDate, SaveUserDto userDto) {
        User user = get(uuid);

        userDtoValidator.test(userDto);

        if(!user.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("USER WAS ALREADY UPDATED");
        }

        userDao.save(toUserConverter.update(user, userDto));
    }

    private List<Role> getAllRoles(){
        return roleDao.findAll();
    }
}
