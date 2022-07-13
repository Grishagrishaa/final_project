package com.example.userservice.service.api;

import com.example.userservice.dao.api.IUserDao;
import com.example.userservice.dao.entity.User;
import com.example.userservice.dto.ReadUserDto;
import com.example.userservice.dto.SaveUserDto;
import com.example.userservice.pagination.MyPage;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Predicate;

@Service
public class UserService implements IUserService {
    private final IUserDao dao;
    private final Predicate<User> validator;
    private final ModelMapper mapper;
    private final Converter<Page<User>, MyPage<ReadUserDto>> pageMyPageConverter;

    public UserService(IUserDao dao, Predicate<User> validator, ModelMapper mapper,
                       Converter<Page<User>, MyPage<ReadUserDto>> pageMyPageConverter) {
        this.dao = dao;
        this.validator = validator;
        this.mapper = mapper;
        this.pageMyPageConverter = pageMyPageConverter;
    }

    @Override
    public User get(UUID uuid) {
        return dao.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("WASN'T FOUND, INCORRECT ID"));
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    public void create(User user) {
        validator.test(user);
        dao.save(user);
    }

    @Override
    public void update(UUID uuid, LocalDateTime updateDate, SaveUserDto userCreateDto) {
        User user = get(uuid);

        if(!user.getUpdateDate().equals(updateDate)){
            throw new OptimisticLockException("USER WAS ALREADY UPDATED");
        }

        mapper.map(userCreateDto, user);

        validator.test(user);//здесь он может зацепиться только за обновленные данные, так как перед сохранением есть валидация

        dao.save(user);
    }

    public Boolean checkNick(String nick){
        return dao.findByNick(nick) != null;
    }

    public Boolean checkMail(String mail){
        return dao.findByMail(mail) != null;
    }

}
