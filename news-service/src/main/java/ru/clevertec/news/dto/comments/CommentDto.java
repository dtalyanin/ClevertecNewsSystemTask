package ru.clevertec.news.dto.comments;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private String username;
    private LocalDateTime time;
}
