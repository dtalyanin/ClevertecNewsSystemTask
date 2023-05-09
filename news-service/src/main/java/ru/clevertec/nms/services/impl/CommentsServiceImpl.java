package ru.clevertec.nms.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.ModificationCommentDto;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.CommentsService;
import ru.clevertec.nms.services.NewsService;
import ru.clevertec.nms.utils.mappers.CommentsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.*;
import static ru.clevertec.nms.utils.SearchHelper.*;
import static ru.clevertec.nms.utils.UserHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository repository;
    private final NewsService newsService;
    private final CommentsMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByNewsIdWithPagination(long newsId, Pageable pageable) {
        newsService.checkNewsWithIdNotExist(newsId, Operation.GET);
        pageable = setPageableUnsorted(pageable);
        return mapper.convertAllCommentsToDtos(repository.findAllByNewsId(newsId, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAllSearchedCommentsByNewsIdWithPagination(long newsId, CommentDto dto, Pageable pageable) {
        newsService.checkNewsWithIdNotExist(newsId, Operation.GET);
        pageable = setPageableUnsorted(pageable);
        Comment newsWithFieldsToSearch = mapper.convertDtoToComment(dto);
        Example<Comment> example = getExample(newsWithFieldsToSearch);
        List<Comment> comments = repository.findAll(example, pageable).getContent();
        return mapper.convertAllCommentsToDtos(comments);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "comments")
    public CommentDto getCommentByIdAndNewsId(long newsId, long commentId) {
        newsService.checkNewsWithIdNotExist(newsId, Operation.GET);
        Optional<Comment> oComment = repository.findByIdAndNewsId(commentId, newsId);
        if (oComment.isEmpty()) {
            throw new NotFoundException(COMMENT_NOT_FOUND + newsId, commentId, ErrorCode.COMMENT_NOT_FOUND);
        }
        return mapper.convertCommentToDto(oComment.get());
    }

    @Override
    public CommentDto addComment(long newsId, ModificationCommentDto dto, AuthenticatedUser user) {
       newsService.checkNewsWithIdNotExist(newsId, Operation.ADD);
        Comment comment = mapper.convertModificationDtoToComment(dto, user.getUsername());
        repository.save(comment);
        return null;
    }

    @Override
    public CommentDto updateComment(long newsId,
                                              long commentId,
                                              ModificationCommentDto dto,
                                              AuthenticatedUser user) {
        newsService.checkNewsWithIdNotExist(newsId, Operation.UPDATE);
        Comment comment = getCommentAndVerifyUserPermissions(commentId, user, Operation.UPDATE);
        mapper.updateComment(comment, dto);
        repository.save(comment);
        return mapper.convertCommentToDto(comment);
    }

    @Override
    public void deleteCommentById(long newsId, long commentId, AuthenticatedUser user) {
        newsService.checkNewsWithIdNotExist(newsId, Operation.DELETE);
        Comment comment = getCommentAndVerifyUserPermissions(commentId, user, Operation.DELETE);
        repository.delete(comment);
    }

    private Comment getCommentById(long id, Operation operation) {
        Optional<Comment> oComment = repository.findById(id);
        if (oComment.isEmpty()) {
            String message = COMMENT_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, id, ErrorCode.COMMENT_NOT_FOUND);
        }
        return oComment.get();
    }

    private Comment getCommentAndVerifyUserPermissions(long commentId, AuthenticatedUser user, Operation operation) {
        Comment comment = getCommentById(commentId, operation);
        if (checkUserCannotPerformOperation(user, comment.getUsername())) {
            String message = NOT_COMMENT_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_MODIFICATION_COMMENT);
        }
        return comment;
    }
}
