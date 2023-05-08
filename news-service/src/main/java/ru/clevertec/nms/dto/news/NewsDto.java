package ru.clevertec.nms.dto.news;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    private String title;
    private String text;
    private String username;
    private LocalDateTime time;
}
