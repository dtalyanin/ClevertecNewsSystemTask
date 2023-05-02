package ru.clevertec.nms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UsersService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return service.getPostById(username);
    }
}
