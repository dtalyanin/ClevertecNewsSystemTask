package ru.clevertec.news.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

import java.util.List;

public interface CommentsService {
    List<CommentDto> getCommentsWithPagination(Pageable pageable);
    List<CommentDto> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable);
    CommentDto getCommentById(long id);

    @Transactional(readOnly = true)
    List<CommentDto> getCommentsByNewsId(long id, Pageable pageable);

    CommentDto addComment(CreateCommentDto dto, AuthenticatedUser user);
    CommentDto updateComment(long id, UpdateCommentDto dto, AuthenticatedUser user);
    void deleteCommentById(long id, AuthenticatedUser user);

    void deleteCommentsByNewsId(long id);

    void triggerCacheEvict(long id);
}
