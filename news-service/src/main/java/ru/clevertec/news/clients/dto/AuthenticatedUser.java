package ru.clevertec.news.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Returned authenticated user from external client with its username and role
 */
@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private Role role;
}
