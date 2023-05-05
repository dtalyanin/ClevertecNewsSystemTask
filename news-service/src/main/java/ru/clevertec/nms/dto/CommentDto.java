package ru.clevertec.nms.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String text;
    private String username;
    private LocalDateTime time;
}
