package ru.clevertec.nms.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.clevertec.nms.clients.dto.UserDto;
import ru.clevertec.nms.clients.services.UsersService;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.security.exceptions.UserNotFoundException;

import java.util.Optional;

import static ru.clevertec.nms.utils.constants.MessageConstants.USERNAME_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDto> dto = service.getUserByUsername(username);
        if (dto.isEmpty()) {
            throw new UserNotFoundException(USERNAME_NOT_FOUND, username, ErrorCode.USER_NOT_FOUND);
        }
        return new UserDetailsDecorator(dto.get());
    }
}
