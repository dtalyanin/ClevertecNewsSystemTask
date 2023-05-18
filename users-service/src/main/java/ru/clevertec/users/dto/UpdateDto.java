package ru.clevertec.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.users.models.Role;

import static ru.clevertec.users.utils.constants.MessageConstants.TOO_SMALL_PASSWORD;

/**
 * DTO for updating comment
 */
@Data
@Schema(description = "DTO for updating comment")
public class UpdateDto {
    @Size(min = 8, message = TOO_SMALL_PASSWORD)
    private String password;
    private Role role;
}
