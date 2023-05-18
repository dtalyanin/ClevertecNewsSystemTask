package ru.clevertec.news.dto.comments;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static ru.clevertec.news.utils.constants.MessageConstants.EMPTY_COMMENT_TEXT;
import static ru.clevertec.news.utils.constants.MessageConstants.NO_NEWS_FOR_COMMENT;

/**
 * DTO for creating comment
 */
@Data
@Schema(description = "DTO for creating comment")
public class CreateCommentDto {
    @NotNull(message = NO_NEWS_FOR_COMMENT)
    private Long newsId;
    @NotBlank(message = EMPTY_COMMENT_TEXT)
    private String text;
}
