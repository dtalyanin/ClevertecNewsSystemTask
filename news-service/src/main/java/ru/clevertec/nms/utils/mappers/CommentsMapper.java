package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.models.Comment;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDto convertCommentToDto(Comment comment);
}
