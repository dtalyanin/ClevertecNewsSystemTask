package ru.clevertec.news.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.clevertec.news.clients.dto.Role;

@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private Role role;
}
