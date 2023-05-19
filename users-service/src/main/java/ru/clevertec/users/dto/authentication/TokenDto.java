package ru.clevertec.users.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Class with generated token
 * @param token generated token
 */
@Schema(description = "Class with generated token")
public record TokenDto(String token) {
}
