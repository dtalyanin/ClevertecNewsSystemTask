package ru.clevertec.news.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private Role role;
}
