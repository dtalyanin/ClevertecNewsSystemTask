package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.ModificationCommentDto;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.CommentsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.nms.utils.SecurityHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@RestController
@RequestMapping("/news/{newsId}/comments")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final CommentsService service;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByNewsIdWithPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId, Pageable pageable) {
        System.out.println(newsId);
        return ResponseEntity.ok(service.getCommentsByNewsIdWithPagination(newsId, pageable));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentByIdAndNewsId(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId,
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long commentId) {
        return ResponseEntity.ok(service.getCommentByIdAndNewsId(newsId, commentId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> getAllSearchedCommentsByNewsIdWithPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId,
            CommentDto dto,
            Pageable pageable) {
        return ResponseEntity.ok(service.getAllSearchedCommentsByNewsIdWithPagination(newsId, dto, pageable));
    }

    @PostMapping
    public ResponseEntity<ModificationResponse> addComment(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId,
            @RequestBody ModificationCommentDto dto) {
        CommentDto commentDto = service.addComment(newsId, dto, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = ;
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(new ModificationResponse(commentDto));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ModificationResponse> updateComment(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId,
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long commentId,
            @RequestBody ModificationCommentDto dto) {
        service.updateComment(newsId, commentId, dto, getAuthenticatedUserFromSecurityContext());
        return ResponseEntity.ok(new ModificationResponse(commentId, COMMENT_UPDATED));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ModificationResponse> deleteCommentById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long newsId,
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long commentId) {
        service.deleteCommentById(newsId, commentId, getAuthenticatedUserFromSecurityContext());
        return ResponseEntity.ok(new ModificationResponse(commentId, COMMENT_DELETED));
    }
}
