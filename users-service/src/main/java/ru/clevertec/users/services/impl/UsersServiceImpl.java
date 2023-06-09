package ru.clevertec.users.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.exceptions.exceptions.UserExistException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.User;
import ru.clevertec.users.security.services.JwtService;
import ru.clevertec.users.services.UsersService;
import ru.clevertec.users.utils.mappers.UsersMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.users.utils.PageableHelper.setPageableUnsorted;
import static ru.clevertec.users.utils.constants.MessageConstants.*;

/**
 * Service for performing operations with users entity in DB
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repository;
    private final JwtService jwtService;
    private final UsersMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsersWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<User> users = repository.findAll(pageable).getContent();
        return mapper.convertAllUsersToDtos(users);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(long id) {
        Optional<User> oUser = repository.findById(id);
        if (oUser.isEmpty()) {
            throw new NotFoundException(USER_ID_NOT_FOUND, id, ErrorCode.USER_ID_NOT_FOUND);
        }
        return mapper.convertUserToDto(oUser.get());
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByToken(String token) {
        String username = jwtService.extractUsername(token);
        Optional<User> oUser = repository.findByUsername(username);
        if (oUser.isEmpty()) {
            throw new TokenException(TOKEN_NOT_VALID, ErrorCode.TOKEN_NOT_VALID);
        }
        jwtService.isTokenExpired(token);
        return mapper.convertUserToDto(oUser.get());
    }

    @Override
    public UserDto addUser(CreateDto dto) {
        if (repository.existsByUsername(dto.getUsername())) {
            throw new UserExistException(USER_EXIST, dto.getUsername(), ErrorCode.USER_EXIST);
        }
        User user = mapper.convertDtoToUser(dto);
        repository.save(user);
        return mapper.convertUserToDto(user);
    }

    @Override
    public UserDto updateUser(long id, UpdateDto dto) {
        Optional<User> oUser = repository.findById(id);
        if (oUser.isEmpty()) {
            throw new NotFoundException(USER_ID_NOT_FOUND + CANNOT_UPDATE_END, id, ErrorCode.USER_ID_NOT_FOUND);
        }
        User user = oUser.get();
        mapper.updateUser(user, dto);
        repository.save(user);
        return mapper.convertUserToDto(user);
    }

    @Override
    public void deleteUserById(long id) {
        int deletedCount = repository.deleteById(id);
        if (deletedCount == 0) {
            throw new NotFoundException(USER_ID_NOT_FOUND + CANNOT_DELETE_END, id, ErrorCode.USER_ID_NOT_FOUND);
        }
    }
}
