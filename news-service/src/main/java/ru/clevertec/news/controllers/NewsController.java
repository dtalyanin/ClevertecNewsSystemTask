package ru.clevertec.news.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.loggers.annotations.ControllerLog;
import ru.clevertec.news.clients.services.UsersService;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.responses.ModificationResponse;
import ru.clevertec.news.services.NewsService;

import java.net.URI;
import java.util.List;

import static ru.clevertec.news.utils.JwtTokenHelper.getJwtTokenFromAuthHeader;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Validated
@ControllerLog
public class NewsController {

    private final NewsService newsService;
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNewsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllNewsWithPagination(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllSearchedNewsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<NewsWithCommentsDto> getNewsByIdWithCommentsPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, Pageable pageable) {
        return ResponseEntity.ok(newsService.getNewsByIdWithCommentsPagination(id, pageable));
    }

    @PostMapping
    public ResponseEntity<ModificationResponse> addNews(
            @RequestBody ModificationNewsDto dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        NewsDto createdDto = newsService.addNews(dto, user);
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody ModificationNewsDto dto) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        NewsDto updatedDto = newsService.updateNews(id, dto, user);
        ModificationResponse response = new ModificationResponse(updatedDto.getId(), NEWS_UPDATED);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        AuthenticatedUser user = usersService.getUserByUsername(getJwtTokenFromAuthHeader(token));
        newsService.deleteNewsById(id, user);
        ModificationResponse response = new ModificationResponse(id, NEWS_DELETED);
        return ResponseEntity.ok(response);
    }
}
