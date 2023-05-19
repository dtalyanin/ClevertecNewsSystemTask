package ru.clevertec.news.clients.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

/**
 * Feign client for performing operations with external client
 */
@FeignClient(value = "users-service")
public interface UsersService {

    /**
     * Get authenticated user info by existing token
     * @param token token for user authenticating
     * @return Authenticated user
     */
    @GetMapping("/users/token/{token}")
    AuthenticatedUser getUserByUsername(@PathVariable("token") String token);
}
