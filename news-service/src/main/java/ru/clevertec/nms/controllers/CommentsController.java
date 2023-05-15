package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.nms.clients.services.UsersService;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.CreateCommentDto;
import ru.clevertec.nms.dto.comments.UpdateCommentDto;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.CommentsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.nms.utils.JwtTokenHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
@ControllerLog
public class CommentsController {

    private final CommentsService commentsService;
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(commentsService.getCommentsWithPagination(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable) {
        return ResponseEntity.ok(commentsService.getAllSearchedCommentsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(commentsService.getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<ModificationResponse> addComment(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody CreateCommentDto dto) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        CommentDto createdDto = commentsService.addComment(dto, user);
        ModificationResponse response = new ModificationResponse(createdDto.getId(), COMMENT_ADDED);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

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
