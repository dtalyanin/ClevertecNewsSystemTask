package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.CreateCommentDto;
import ru.clevertec.nms.dto.comments.UpdateCommentDto;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDto convertCommentToDto(Comment comment);
    Comment convertDtoToComment(CommentDto dto);
    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);

    Comment convertCreateDtoToComment(CreateCommentDto dto, News news, String username);
    Comment convertUpdateDtoToComment(UpdateCommentDto dto, String username);

    void updateComment(@MappingTarget Comment comment, UpdateCommentDto dto);
}
