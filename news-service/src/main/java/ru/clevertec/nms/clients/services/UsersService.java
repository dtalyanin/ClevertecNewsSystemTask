package ru.clevertec.nms.clients.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.nms.clients.dto.UserDto;

import java.util.Optional;

@FeignClient(value = "users-service", url = "http://localhost:8090")
public interface UsersService {

    @GetMapping( "/users/{username}")
    Optional<UserDto> getUserByUsername(@PathVariable("username") String username);
}
