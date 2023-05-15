package ru.clevertec.users.security.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.models.User;
import ru.clevertec.users.security.models.SimpleUserDetails;

import java.util.Optional;

import static ru.clevertec.users.utils.constants.MessageConstants.USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> oUser = repository.findByUsername(username);
        if (oUser.isEmpty()) {
            throw new NotFoundException(USERNAME_NOT_FOUND, username, ErrorCode.USER_ID_NOT_FOUND);
        }
        return new SimpleUserDetails(oUser.get());
    }
}
