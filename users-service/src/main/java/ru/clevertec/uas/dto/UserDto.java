package ru.clevertec.uas.dto;

import lombok.Data;
import ru.clevertec.uas.models.Role;

@Data
public class UserDto {
    private String username;
    private String password;
    private Role role;
}
