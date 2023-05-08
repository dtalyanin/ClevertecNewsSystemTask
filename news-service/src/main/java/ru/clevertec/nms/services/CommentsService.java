package ru.clevertec.nms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.clients.dto.Role;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.ModificationCommentDto;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.utils.PageableHelper;
import ru.clevertec.nms.utils.SearchHelper;
import ru.clevertec.nms.utils.mappers.CommentsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository repository;
    private final NewsRepository newsRepository;
    private final CommentsMapper mapper;

    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByNewsIdWithPagination(long newsId, Pageable pageable) {
        checkNewsWithIdNotExist(newsId);
        pageable = PageableHelper.setPageableUnsorted(pageable);
        return mapper.convertAllCommentsToDtos(repository.findAllByNewsId(newsId, pageable));
    }

    @Transactional(readOnly = true)
    public List<CommentDto> getAllSearchedCommentsByNewsIdWithPagination(long newsId, CommentDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        Comment newsWithFieldsToSearch = mapper.convertDtoToComment(dto);
        Example<Comment> example = SearchHelper.getExample(newsWithFieldsToSearch);
        List<Comment> comments = repository.findAll(example, pageable).getContent();
        return mapper.convertAllCommentsToDtos(comments);
    }

    @Transactional(readOnly = true)
    public CommentDto getCommentByIdAndNewsId(long newsId, long commentId) {
        checkNewsWithIdNotExist(newsId);
        Optional<Comment> oComment = repository.findByIdAndNewsId(commentId, newsId);
        if (oComment.isEmpty()) {
            throw new NotFoundException(COMMENT_NOT_FOUND + NOT_FOUND_WITH_NEWS_ID + newsId, commentId, ErrorCode.COMMENT_NOT_FOUND);
        }
        return mapper.convertCommentToDto(oComment.get());
    }

    public ModificationResponse addComment(long newsId, ModificationCommentDto dto, AuthenticatedUser user) {
        Optional<News> oNews = newsRepository.findById(newsId);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS_WITH_ID_NOT_FOUND + NOT_FOUND + CANNOT_ADD_END, newsId, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        Comment comment = mapper.convertModificationDtoToComment(dto);
        news.addComment(comment);
        comment.setUsername(user.getUsername());
        repository.save(comment);
        return new ModificationResponse(comment.getId(), COMMENT_ADDED);
    }

    public ModificationResponse updateComment(long newsId,
                                              long commentId,
                                              ModificationCommentDto dto,
                                              AuthenticatedUser user) {
        if (!newsRepository.existsById(newsId)) {
            throw new NotFoundException(NEWS_WITH_ID_NOT_FOUND + NOT_FOUND + CANNOT_UPDATE_END, newsId, ErrorCode.NEWS_NOT_FOUND);
        }
        Comment comment = getCommentAndVerifyUserPermissions(commentId, user, Operation.UPDATE);
        mapper.updateComment(comment, dto);
        repository.save(comment);
        return new ModificationResponse(commentId, COMMENT_UPDATED);
    }

    private Comment getCommentAndVerifyUserPermissions(long commentId, AuthenticatedUser user, Operation operation) {
        Optional<Comment> oComment = repository.findById(commentId);
        if (oComment.isEmpty()) {
            String message = COMMENT_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, commentId, ErrorCode.COMMENT_NOT_FOUND);
        }
        Comment comment = oComment.get();
        if (checkUserCannotPerformOperation(user, comment)) {
            String message = NOT_COMMENT_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_MODIFICATION_COMMENT);
        }
        return comment;
    }

    public ModificationResponse deleteCommentById(long newsId, long commentId, AuthenticatedUser user) {
        Optional<News> oNews = newsRepository.findById(newsId);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS_WITH_ID_NOT_FOUND + NOT_FOUND, newsId, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        Comment comment = getCommentAndVerifyUserPermissions(commentId, user, Operation.DELETE);
        news.deleteComment(comment);
        repository.delete(comment);
        return new ModificationResponse(commentId, COMMENT_DELETED);
    }

    private void checkNewsWithIdNotExist(long id) {
        if (!newsRepository.existsById(id)) {
            throw new NotFoundException(NEWS_WITH_ID_NOT_FOUND + NOT_FOUND, id, ErrorCode.NEWS_NOT_FOUND);
        }
    }

    private boolean checkUserCannotPerformOperation(AuthenticatedUser user, Comment comment) {
        return !checkUserIsAdmin(user) && !checkUserIsNewsOwner(user, comment);
    }

    private boolean checkUserIsNewsOwner(AuthenticatedUser user, Comment comment) {
        return comment.getUsername().equals(user.getUsername());
    }

    private boolean checkUserIsAdmin(AuthenticatedUser user) {
        return user.getAuthorities().contains(Role.ADMIN.getNameWithPrefix());
    }
}
