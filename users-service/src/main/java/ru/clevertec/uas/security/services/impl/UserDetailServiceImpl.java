package ru.clevertec.uas.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.clevertec.uas.dao.UsersRepository;
import ru.clevertec.uas.exceptions.ErrorCode;
import ru.clevertec.uas.exceptions.UserNotFoundException;
import ru.clevertec.uas.models.User;
import ru.clevertec.uas.security.models.SimpleUserDetails;

import java.util.Optional;

import static ru.clevertec.uas.utils.constants.MessageConstants.USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> oUser = repository.findByUsername(username);
        if (oUser.isEmpty()) {
            throw new UserNotFoundException(USERNAME_NOT_FOUND, username, ErrorCode.USERNAME_NOT_FOUND);
        }
        return new SimpleUserDetails(oUser.get());
    }
}