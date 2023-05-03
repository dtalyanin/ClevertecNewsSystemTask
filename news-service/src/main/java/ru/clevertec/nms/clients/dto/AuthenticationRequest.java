package ru.clevertec.nms.clients.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Data
public class AuthenticationRequest {
    @NotBlank(message = EMPTY_USER_NAME)
    private String username;
    @NotBlank(message = EMPTY_PASSWORD)
    @Size(message = PASSWORD_MIN_LENGTH_ERROR)
    private String password;
}
