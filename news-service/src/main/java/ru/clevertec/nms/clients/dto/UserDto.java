package ru.clevertec.nms.clients.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private Role role;
}
