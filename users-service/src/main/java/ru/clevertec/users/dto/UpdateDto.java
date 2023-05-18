package ru.clevertec.users.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.users.models.Role;

import static ru.clevertec.users.utils.constants.MessageConstants.TOO_SMALL_PASSWORD;

/**
 * DTO for updating comment
 */
@Data
public class UpdateDto {
    @Size(min = 8, message = TOO_SMALL_PASSWORD)
    private String password;
    private Role role;
}
