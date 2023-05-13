package ru.clevertec.uas.dto.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static ru.clevertec.uas.utils.constants.MessageConstants.*;

public record AuthenticationDto(@NotBlank(message = EMPTY_USER_NAME)
                                    String username,
                                @NotBlank(message = EMPTY_PASSWORD) @Size(message = TOO_SMALL_PASSWORD)
                                    String password) {
}
