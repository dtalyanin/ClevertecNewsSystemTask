package ru.clevertec.nms.dto.comments;

import lombok.Data;

@Data
public class CreateCommentDto {
    private Long id;
    private String text;
}
