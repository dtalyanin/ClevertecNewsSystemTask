package ru.clevertec.nms.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.clients.dto.Permission;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.CreateCommentDto;
import ru.clevertec.nms.dto.comments.UpdateCommentDto;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.services.CommentsService;
import ru.clevertec.nms.utils.mappers.CommentsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.setPageableUnsorted;
import static ru.clevertec.nms.utils.SearchHelper.getExample;
import static ru.clevertec.nms.utils.UserHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository repository;
    private final NewsRepository newsRepository;
    private final CommentsMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        return mapper.convertAllCommentsToDtos(repository.findAll(pageable).getContent());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        Comment newsWithFieldsToSearch = mapper.convertDtoToComment(dto);
        Example<Comment> example = getExample(newsWithFieldsToSearch);
        List<Comment> comments = repository.findAll(example, pageable).getContent();
        return mapper.convertAllCommentsToDtos(comments);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "comments")
    public CommentDto getCommentById(long id) {
        Comment comment = getCommentByIdIfExist(id, Operation.GET);
        return mapper.convertCommentToDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByNewsId(long id, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<Comment> comments = repository.findAllByNewsId(id, pageable);
        return mapper.convertAllCommentsToDtos(comments);
    }

    @Override
    @CachePut(value = "comments", key = "#result.id")
    public CommentDto addComment(CreateCommentDto dto, AuthenticatedUser user) {
        checkUserHasPermission(user, Operation.ADD);
        Comment comment = mapper.convertCreateDtoToComment(dto, user.getUsername());
        Optional<News> oNews = newsRepository.findById(dto.getNewsId());
        if (oNews.isEmpty()) {
            String message = NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.ADD.getName();
            throw new NotFoundException(message, dto.getNewsId(), ErrorCode.NEWS_NOT_FOUND);
        }
        comment.setNews(oNews.get());
        repository.save(comment);
        return mapper.convertCommentToDto(comment);
    }

    @Override
    @CachePut(value = "comments", key = "#result.id")
    public CommentDto updateComment(long id, UpdateCommentDto dto, AuthenticatedUser user) {
        Comment comment = getCommentAndVerifyUserPermissions(id, user, Operation.UPDATE);
        mapper.updateComment(comment, dto);
        repository.save(comment);
        return mapper.convertCommentToDto(comment);
    }

    @Override
    @CacheEvict(value = "comments", key = "#id")
    public void deleteCommentById(long id, AuthenticatedUser user) {
        Comment comment = getCommentAndVerifyUserPermissions(id, user, Operation.DELETE);
        repository.delete(comment);
    }

    @Override
    public void deleteCommentsByNewsId(long id) {
        repository.deleteByNewsId(id);
    }

    @Override
    @CacheEvict(value = "comments")
    public void triggerCacheEvict(long id) {
    }

    private Comment getCommentByIdIfExist(long id, Operation operation) {
        Optional<Comment> oComment = repository.findById(id);
        if (oComment.isEmpty()) {
            String message = COMMENT_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, id, ErrorCode.COMMENT_NOT_FOUND);
        }
        return oComment.get();
    }

    private Comment getCommentAndVerifyUserPermissions(long id, AuthenticatedUser user, Operation operation) {
        checkUserHasPermission(user, operation);
        Comment comment = getCommentByIdIfExist(id, operation);
        checkUserIsCommentOwner(user, comment, operation);
        return comment;
    }

    private void checkUserHasPermission(AuthenticatedUser user, Operation operation) {
        if (checkUserHasNotPermission(user, Permission.COMMENTS_MENAGE)) {
            String message = NOT_PERMISSIONS + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NO_PERMISSIONS_FOR_COMMENT_MODIFICATION);
        }
    }

    private void checkUserIsCommentOwner(AuthenticatedUser user, Comment comment, Operation operation) {
        if (checkUserIsNotOwner(user, comment.getUsername())) {
            String message = NOT_COMMENT_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_COMMENT_MODIFICATION);
        }
    }
}
