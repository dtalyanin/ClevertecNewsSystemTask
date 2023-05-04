package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDto convertCommentToDto(Comment comment);

    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);
}
