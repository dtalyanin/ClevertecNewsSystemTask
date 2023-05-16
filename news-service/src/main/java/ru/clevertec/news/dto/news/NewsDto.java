package ru.clevertec.news.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsDto {
    @JsonIgnore
    private Long id;
    private String title;
    private String text;
    private String username;
    private LocalDateTime time;
}
