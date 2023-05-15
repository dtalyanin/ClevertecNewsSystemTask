package ru.clevertec.news.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    CommentDto convertCommentToDto(Comment comment);
    Comment convertDtoToComment(CommentDto dto);
    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);
    Comment convertCreateDtoToComment(CreateCommentDto dto, String username);
    void updateComment(@MappingTarget Comment comment, UpdateCommentDto dto);
}
