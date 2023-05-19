package ru.clevertec.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.users.models.Role;

import static ru.clevertec.users.utils.constants.MessageConstants.*;

/**
 * DTO for creating user
 */
@Data
@Schema(description = "DTO for creating user")
public class CreateDto {
    @NotBlank(message = EMPTY_USERNAME)
    @Size(max = 50, message = TOO_BIG_USERNAME)
    private String username;
    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = TOO_SMALL_PASSWORD)
    private String password;
    @NotNull(message = NULL_ROLE)
    private Role role;
}
