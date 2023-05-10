package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.nms.dto.news.ModificationNewsDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.NewsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.nms.utils.SecurityHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Validated
public class NewsController {

    private final NewsService service;

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNewsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(service.getAllNewsWithPagination(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable) {
        return ResponseEntity.ok(service.getAllSearchedNewsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.getNewsById(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<NewsDto> getNewsByIdWithCommentsPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, Pageable pageable) {
        return ResponseEntity.ok(service.getNewsByIdWithCommentsPagination(id, pageable));
    }

    @PostMapping
    public ResponseEntity<ModificationResponse> addNews(@RequestBody ModificationNewsDto dto) {
        NewsDto createdDto = service.addNews(dto, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = new ModificationResponse(createdDto.getId(), NEWS_ADDED);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateNews(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestBody ModificationNewsDto dto) {
        NewsDto updatedDto = service.updateNews(id, dto, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = new ModificationResponse(updatedDto.getId(), NEWS_UPDATED);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        service.deleteNewsById(id, getAuthenticatedUserFromSecurityContext());
        ModificationResponse response = new ModificationResponse(id, NEWS_DELETED);
        return ResponseEntity.ok(response);
    }
}
