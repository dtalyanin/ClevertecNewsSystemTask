package ru.clevertec.nms.clients.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.nms.clients.dto.UserDto;
import ru.clevertec.nms.models.AuthenticatedUser;

import java.util.Optional;

@FeignClient(value = "users-service", url = "http://localhost:8090")
public interface UsersService {

    @GetMapping( "/auth/token/{username}")
    AuthenticatedUser getUserByUsername(@PathVariable("username") String username);
}
