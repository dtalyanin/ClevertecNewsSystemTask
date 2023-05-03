package ru.clevertec.nms.dto.news;

import lombok.Data;
import ru.clevertec.nms.dto.CommentDto;

import java.util.List;

@Data
public class NewsWithCommentsDto extends NewsDto {
    private List<CommentDto> comments;
}
