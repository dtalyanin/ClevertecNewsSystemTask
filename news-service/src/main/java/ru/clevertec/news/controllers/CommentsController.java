package ru.clevertec.news.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.clients.services.UsersService;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.models.responses.ModificationResponse;
import ru.clevertec.news.services.CommentsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.news.utils.JwtTokenHelper.getJwtTokenFromAuthHeader;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

/**
 * Controller for performing operations with comments entity
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
@ControllerLog
public class CommentsController {

    private final CommentsService commentsService;
    private final UsersService usersService;

    /**
     * Get all existing comments and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of comments DTO
     */
    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(commentsService.getCommentsWithPagination(pageable));
    }

    /**
     * Get all comments by request params and return them according to chosen size and page
     * @param dto value with fields for search
     * @param pageable page and maximum size of returning collections
     * @return list of founded comments DTO
     */
    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable) {
        return ResponseEntity.ok(commentsService.getAllSearchedCommentsWithPagination(dto, pageable));
    }

    /**
     * Get comment with specified ID
     * @param id ID to search
     * @return comment DTO with specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(commentsService.getCommentById(id));
    }

    /**
     * Add new comment to repository
     * @param token token for user authentication
     * @param dto comment DTO to add
     * @return response with created ID
     */
    @PostMapping
    public ResponseEntity<ModificationResponse> addComment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody @Valid CreateCommentDto dto) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        CommentDto createdDto = commentsService.addComment(dto, user);
        ModificationResponse response = new ModificationResponse(createdDto.getId(), COMMENT_ADDED);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    /**
     * Update comment with specified ID to values that contain DTO
     * @param id ID to update
     * @param token token for user authentication
     * @param dto DTO with values to update
     * @return response with updated ID
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateComment(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody UpdateCommentDto dto) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        CommentDto updatedDto = commentsService.updateComment(id, dto, user);
        ModificationResponse response = new ModificationResponse(updatedDto.getId(), COMMENT_UPDATED);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete comment with specified ID
     * @param id ID to delete
     * @param token token for user authentication
     * @return response with deleted ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteCommentById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        commentsService.deleteCommentById(id, user);
        ModificationResponse response = new ModificationResponse(id, COMMENT_DELETED);
        return ResponseEntity.ok(response);
    }
}
