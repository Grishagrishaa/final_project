package com.example.userservice.service;

import com.example.userservice.converters.SaveUserDtoToUserConverter;
import com.example.userservice.converters.UserToUserDetailsConverter;
import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.entity.api.IRoleDao;
import com.example.userservice.dao.entity.api.IUserDao;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dao.entity.enums.ERole;
import com.example.userservice.dao.entity.enums.EStatus;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.service.api.IUserService;
import com.example.userservice.validation.SaveUserDtoPredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.userservice.dao.entity.enums.EStatus.WAITING_ACTIVATION;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService, IUserService {
    private final SaveUserDtoPredicate userDtoValidator;
    private final UserToUserDetailsConverter toUserDetailsConverter;
    private final SaveUserDtoToUserConverter toUserConverter;
    private final IUserDao userDao;
    private final IRoleDao roleDao;


    public UserService(SaveUserDtoPredicate userDtoValidator,
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
        return toUserDetailsConverter.convert(userDao.findByNick(nick)
                .orElseThrow( () -> new IllegalArgumentException("INVALID USERNAME ")));
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
    @Transactional
    public void createUser(SaveUserDto userDto) {
        //TODO SETTING DEFAULT VALUES IN CASE OF NULL VALUES IN NECESSARY FIELDS
        userDtoValidator.test(userDto);

        User user = toUserConverter.convert(userDto);

        setDefaults(user);

        userDao.save(user);
    }



    @Override
    @Transactional
    public void updateUser(UUID uuid, LocalDateTime updateDate, SaveUserDto userDto) {
        User user = get(uuid);

        userDtoValidator.testUpdate(userDto);

        if(!user.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("USER WAS ALREADY UPDATED");
        }

        toUserConverter.update(user, userDto);

        userDao.save(user);
    }

    private void setDefaults(User user){
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(Stream.of(new Role(2L))
                    .collect(Collectors.toCollection(HashSet::new)) );//2 - ID OF USER, 1 - ADMIN
        }

        if(user.getStatus() == null){
            user.setStatus(WAITING_ACTIVATION);
        }
    }
}
