package ru.clevertec.news.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response with ID that was affected during operation and message about execution
 */
@Schema(description = "Response with ID that was affected during operation and message about execution")
@AllArgsConstructor
@Getter
public class ModificationResponse {
    private long id;
    private String message;
}
