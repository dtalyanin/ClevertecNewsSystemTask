package ru.clevertec.users.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static ru.clevertec.users.utils.constants.MessageConstants.*;

/**
 * Request params for user authentication
 * @param username
 * @param password
 */
@Schema(description = "Request params for user authentication")
public record AuthenticationDto(@NotBlank(message = EMPTY_USER_NAME) String username,
                                @NotBlank(message = EMPTY_PASSWORD)
                                @Size(min = 8, message = TOO_SMALL_PASSWORD) String password) {
}
