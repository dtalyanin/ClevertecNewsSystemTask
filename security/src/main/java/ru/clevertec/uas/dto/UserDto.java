package ru.clevertec.uas.dto;

import lombok.Data;
import ru.clevertec.uas.models.Role;

@Data
public class UserDto {
    private String userName;
    private Role role;
}
