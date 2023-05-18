package ru.clevertec.news.dto.news;

import lombok.Data;
import ru.clevertec.news.dto.comments.CommentDto;

import java.util.List;

/**
 * DTO representation of news and its comments
 */
@Data
public class NewsWithCommentsDto extends NewsDto {
    private List<CommentDto> comments;
}
