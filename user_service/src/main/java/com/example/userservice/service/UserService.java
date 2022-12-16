package com.example.userservice.service;

import com.example.userservice.dao.entity.Role;
import com.example.userservice.dao.api.IUserDao;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.users.SaveUserDto;
import com.example.userservice.security.UserDetailsUser;
import com.example.userservice.service.api.IUserService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.userservice.dao.entity.enums.EStatus.WAITING_ACTIVATION;

@Service
@Validated
@Transactional(readOnly = true)
public class UserService implements IUserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final ConversionService conversionService;
    private final IUserDao userDao;


    public UserService(
                       ConversionService conversionService,
                       IUserDao userDao) {
        this.conversionService = conversionService;
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String nick) throws UsernameNotFoundException {
        User user = userDao.findByNick(nick).orElseThrow(() -> new IllegalArgumentException("INVALID USERNAME "));

        log.info("LOAD USER_DETAILS - {}", user);
        return conversionService.convert(user, UserDetailsUser.class);
    }

    @Override
    @Cacheable(cacheNames = "user")
    public User get(UUID uuid) {
        User user = userDao.findById(uuid).orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        log.info("GET USER - {}", user);
        return user;
    }

    @Override
    @Cacheable(cacheNames = "user")
    public Page<User> loadAll(Pageable pageable) {
        Page<User> all = userDao.findAll(pageable);
        log.info("GET PAGE OF USERS, ELEMENTS - {}", all.getTotalElements());
        return all;
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "user")
    public User createUser(@Valid SaveUserDto userDto) {
        User user = conversionService.convert(userDto, User.class);

        setDefaults(user);

        User saved = userDao.save(user);
        log.info("SAVE USER - {}", user);

        return get(saved.getUuid());
    }



    @Override
    @Transactional
    @CachePut(cacheNames = "user")
    public User updateUser(UUID uuid, LocalDateTime updateDate, @Valid SaveUserDto userDto) {
        User user = get(uuid);

        if(!user.getUpdateDate().isEqual(updateDate)){
            throw new OptimisticLockException("USER WAS ALREADY UPDATED");
        }

        User updated = conversionService.convert(userDto, User.class);

        updated.setUuid(user.getUuid());
        updated.setCreateDate(user.getCreateDate());
        updated.setUpdateDate(user.getUpdateDate());

        User saved = userDao.save(updated);//todo check it
        log.info("UPDATE USER- {}", user);
        return saved;

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
