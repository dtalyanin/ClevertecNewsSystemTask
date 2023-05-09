package ru.clevertec.nms.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.CreateCommentDto;
import ru.clevertec.nms.dto.comments.UpdateCommentDto;
import ru.clevertec.nms.models.AuthenticatedUser;

import java.util.List;

public interface CommentsService {
    List<CommentDto> getCommentsWithPagination(Pageable pageable);
    List<CommentDto> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable);
    CommentDto getCommentById(long id);
    CommentDto addComment(CreateCommentDto dto, AuthenticatedUser user);
    CommentDto updateComment(long id, UpdateCommentDto dto, AuthenticatedUser user);
    void deleteCommentById(long id, AuthenticatedUser user);
}
