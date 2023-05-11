package ru.clevertec.nms.clients.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.nms.models.AuthenticatedUser;

@FeignClient(value = "users-service", url = "http://localhost:8090")
public interface UsersService {

    @GetMapping("/users/token/{token}")
    AuthenticatedUser getUserByUsername(@PathVariable("token") String token);
}
