package ru.clevertec.uas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.uas.models.Role;

import static ru.clevertec.uas.utils.constants.MessageConstants.*;

@Data
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
