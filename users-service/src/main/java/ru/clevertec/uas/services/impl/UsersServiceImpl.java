package ru.clevertec.uas.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.uas.dao.UsersRepository;
import ru.clevertec.uas.dto.CreateDto;
import ru.clevertec.uas.dto.UpdateDto;
import ru.clevertec.uas.dto.UserDto;
import ru.clevertec.uas.dto.UserDtoWithPassword;
import ru.clevertec.uas.exceptions.UserExistException;
import ru.clevertec.uas.exceptions.UserNotFoundException;
import ru.clevertec.uas.models.User;
import ru.clevertec.uas.models.responses.ModificationResponse;
import ru.clevertec.uas.services.UsersService;
import ru.clevertec.uas.utils.mappers.UsersMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.uas.utils.constants.MessageConstants.*;
import static ru.clevertec.uas.utils.constants.MessageConstants.USER_DELETED;

@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;


    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        return mapper.convertAllUsersToDtos(repository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        Optional<User> oUser = repository.findById(id);
        if (oUser.isEmpty()) {
            throw new UserNotFoundException(USER_ID_NOT_FOUND, id);
        }
        return mapper.convertUserToDto(oUser.get());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDtoWithPassword getUserByUsername(String username) {
        Optional<User> oUser = repository.findByUsername(username);
        if (oUser.isEmpty()) {
            throw new UserNotFoundException(USERNAME_NOT_FOUND, username);
        }
        return mapper.convertUserToDtoWithPassword(oUser.get());
    }

    @Override
    public ModificationResponse addUser(CreateDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new UserExistException(USER_EXIST, dto.getUsername());
        }
        User user = mapper.convertDtoToUser(dto);
        repository.save(user);
        return new ModificationResponse(user.getId(), USER_ADDED);
    }

    @Override
    public ModificationResponse updateUser(long id, UpdateDto dto) {
        Optional<User> oUser = repository.findById(id);
        if (oUser.isEmpty()) {
            throw new UserNotFoundException(USER_ID_NOT_FOUND + CANNOT_UPDATE_END, id);
        }
        User user = oUser.get();
        mapper.updateUser(user, dto);
        repository.save(user);
        return new ModificationResponse(id, USER_UPDATED);
    }

    @Override
    public ModificationResponse deleteUserById(long id) {
        int deletedCount = repository.deleteById(id);
        if (deletedCount == 0) {
            throw new UserNotFoundException(USER_ID_NOT_FOUND + CANNOT_DELETE_END, id);
        }
        return new ModificationResponse(id, USER_DELETED);
    }

}
