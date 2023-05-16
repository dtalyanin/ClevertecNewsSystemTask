package ru.clevertec.news.dto.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    @JsonIgnore
    private Long id;
    private String text;
    private String username;
    private LocalDateTime time;
}
