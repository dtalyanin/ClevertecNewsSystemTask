package ru.clevertec.news.dto.comments;

import lombok.Data;

@Data
public class CreateCommentDto {
    private Long newsId;
    private String text;
}
