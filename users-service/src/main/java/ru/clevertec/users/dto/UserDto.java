package ru.clevertec.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.clevertec.users.models.Role;

/**
 * DTO representation of user
 */
@Data
@Schema(description = "DTO representation of user")
public class UserDto {
    @JsonIgnore
    private Long id;
    private String username;
    private Role role;
}
