package ru.clevertec.news.dto.news;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.clevertec.news.dto.comments.CommentDto;

import java.util.List;

/**
 * DTO representation of news and its comments
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "DTO representation of news and its comments")
public class NewsWithCommentsDto extends NewsDto {
    private List<CommentDto> comments;
}
