package ru.clevertec.users.dto;

import lombok.Data;
import ru.clevertec.users.models.Role;

@Data
public class UserDto {
    private String username;
    private Role role;
}
