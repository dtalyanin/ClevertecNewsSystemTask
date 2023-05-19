package ru.clevertec.news.dto.news;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO representation of news
 */
@Data
@Schema(description = "DTO representation of news")
public class NewsDto {
    @JsonIgnore
    private Long id;
    private String title;
    private String text;
    private String username;
    private LocalDateTime time;
}
