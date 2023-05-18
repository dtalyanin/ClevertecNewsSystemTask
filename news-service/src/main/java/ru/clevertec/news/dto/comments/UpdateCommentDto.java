package ru.clevertec.news.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for updating comment
 */
@Data
@Schema(description = "DTO for updating comment")
public class UpdateCommentDto {
    private String text;
}
