package ru.clevertec.news.utils.mappers;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Validated
public interface CommentsMapper {

    CommentDto convertCommentToDto(Comment comment);
    Comment convertDtoToComment(CommentDto dto);
    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);
    @Valid Comment convertCreateDtoToComment(CreateCommentDto dto, String username);
    @Valid Comment updateComment(@MappingTarget Comment comment, UpdateCommentDto dto);
}
