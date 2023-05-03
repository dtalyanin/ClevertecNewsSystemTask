package ru.clevertec.uas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.uas.dao.UsersRepository;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.models.User;
import ru.clevertec.uas.utils.mappers.UsersMapper;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;

    @Autowired
    public UsersService(UsersRepository repository, UsersMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDto getUserByName(String userName) {
        Optional<User> oUser = repository.findByUsername(userName);
        if (oUser.isEmpty()) {
            throw new RuntimeException("aas");
        }
        System.out.println(oUser.get());
        UserDto dto = mapper.convertUserToDto(oUser.get());
        System.out.println(dto);
        return dto;
    }
}
