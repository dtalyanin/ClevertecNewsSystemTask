package ru.clevertec.nms.dto.comments;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentDto implements Serializable {
    private Long id;
    private String text;
    private String username;
    private LocalDateTime time;
}
