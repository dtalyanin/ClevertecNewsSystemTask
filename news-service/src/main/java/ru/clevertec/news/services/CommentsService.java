package ru.clevertec.news.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;

import java.util.List;

/**
 * Service for performing CRUD operation with comments
 */
public interface CommentsService {

    /**
     * Get all existing comments and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of comments DTO
     */
    List<CommentDto> getCommentsWithPagination(Pageable pageable);

    /**
     * Get all comments by request params and return them according to chosen size and page
     * @param dto value with fields for search
     * @param pageable page and maximum size of returning collections
     * @return list of founded comments DTO
     */
    List<CommentDto> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable);

    /**
     * Get comment with specified ID
     * @param id ID to search
     * @return comment DTO with specified ID
     */
    CommentDto getCommentById(long id);

    /**
     * Get all comments that associated with specified news ID
     * @param id news ID to search
     * @param pageable page and maximum size of returning collections
     * @return list of comments DTO that associated with specified news ID
     */
    List<CommentDto> getCommentsByNewsId(long id, Pageable pageable);

    /**
     * Add new comment to repository
     * @param user authenticated user who performs operation
     * @param dto comment to add
     * @return created comment DTO
     */
    CommentDto addComment(CreateCommentDto dto, AuthenticatedUser user);

    /**
     * Update comment with specified ID to values that contain DTO
     * @param id ID to update
     * @param user authenticated user who performs operation
     * @param dto DTO with values to update
     * @return updated comment DTO
     */
    CommentDto updateComment(long id, UpdateCommentDto dto, AuthenticatedUser user);

    /**
     * Delete comment with specified ID
     * @param id ID to delete
     * @param user authenticated user who performs operation
     */
    void deleteCommentById(long id, AuthenticatedUser user);

    /**
     * Delete comment related to specified news ID
     * @param id news ID to delete
     */
    void deleteCommentsByNewsId(long id);

    /**
     * Helper method for delete comment by ID from cache
     * @param id ID to delete from cache
     */
    void triggerCacheEvict(long id);
}
