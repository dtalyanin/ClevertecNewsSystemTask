package ru.clevertec.news.dto.news;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    private Long id;
    private String title;
    private String text;
    private String username;
    private LocalDateTime time;
}
