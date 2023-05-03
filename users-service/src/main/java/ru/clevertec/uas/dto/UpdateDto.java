package ru.clevertec.uas.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.clevertec.uas.models.Role;

import static ru.clevertec.uas.utils.constants.MessageConstants.TOO_SMALL_PASSWORD;

@Data
public class UpdateDto {
    @Size(min = 8, message = TOO_SMALL_PASSWORD)
    private String password;
    private Role role;
}
