package ru.clevertec.users.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response with ID that was affected during operation and message about execution
 */
@Schema(description = "Response with ID that was affected during operation and message about execution")
public record ModificationResponse(long id, String message) {
}
