package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.ModificationCommentDto;
import ru.clevertec.nms.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDto convertCommentToDto(Comment comment);
    Comment convertDtoToComment(CommentDto dto);
    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);

    Comment convertModificationDtoToComment(ModificationCommentDto dto, String username);

    void updateComment(@MappingTarget Comment comment, ModificationCommentDto dto);
}
