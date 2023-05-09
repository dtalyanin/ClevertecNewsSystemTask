package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.comments.CreateCommentDto;
import ru.clevertec.nms.dto.comments.UpdateCommentDto;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.CommentsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.nms.utils.SecurityHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Validated
public class CommentsController {

    private final CommentsService service;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(service.getCommentsWithPagination(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommentDto>> getAllSearchedCommentsWithPagination(CommentDto dto, Pageable pageable) {
        return ResponseEntity.ok(service.getAllSearchedCommentsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.getCommentById(id));
    }


    @PostMapping
    public ResponseEntity<ModificationResponse> addComment(@RequestBody CreateCommentDto dto) {
        CommentDto createdDto = service.addComment(dto, getAuthenticatedUserFromSecurityContext());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId()).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateComment(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestBody UpdateCommentDto dto) {
        CommentDto updatedDto = service.updateComment(id, dto, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = new ModificationResponse(updatedDto.getId(), COMMENT_DELETED);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteCommentById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        service.deleteCommentById(id, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = new ModificationResponse(id, COMMENT_DELETED);
        return ResponseEntity.ok(response);
    }
}
