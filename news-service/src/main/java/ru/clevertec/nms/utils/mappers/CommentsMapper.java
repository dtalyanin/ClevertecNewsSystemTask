package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.ModificationCommentDto;
import ru.clevertec.nms.models.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    CommentDto convertCommentToDto(Comment comment);
    Comment convertDtoToComment(CommentDto dto);
    List<CommentDto> convertAllCommentsToDtos(List<Comment> comments);

    Comment convertModificationDtoToComment(ModificationCommentDto dto);

    void updateComment(@MappingTarget Comment comment, ModificationCommentDto dto);
}
