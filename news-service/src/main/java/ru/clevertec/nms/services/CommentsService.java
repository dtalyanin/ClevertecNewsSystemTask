package ru.clevertec.nms.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.ModificationCommentDto;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.responses.ModificationResponse;

import java.util.List;

public interface CommentsService {
    @Transactional(readOnly = true)
    List<CommentDto> getCommentsByNewsIdWithPagination(long newsId, Pageable pageable);

    @Transactional(readOnly = true)
    List<CommentDto> getAllSearchedCommentsByNewsIdWithPagination(long newsId, CommentDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    CommentDto getCommentByIdAndNewsId(long newsId, long commentId);

    ModificationResponse addComment(long newsId, ModificationCommentDto dto, AuthenticatedUser user);

    ModificationResponse updateComment(long newsId,
                                       long commentId,
                                       ModificationCommentDto dto,
                                       AuthenticatedUser user);

    ModificationResponse deleteCommentById(long newsId, long commentId, AuthenticatedUser user);
}
