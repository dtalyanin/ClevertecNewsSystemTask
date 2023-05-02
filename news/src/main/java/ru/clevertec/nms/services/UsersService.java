package ru.clevertec.nms.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.clevertec.nms.dto.UserDto;

@FeignClient(value = "security", url = "http://localhost:5000")
public interface UsersService {

    @GetMapping( "/users/{name}")
    UserDto getPostById(@PathVariable("name") String userName);
}
