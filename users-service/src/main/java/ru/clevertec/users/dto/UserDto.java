package ru.clevertec.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.clevertec.users.models.Role;

@Data
public class UserDto {
    @JsonIgnore
    private Long id;
    private String username;
    private Role role;
}
