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

/**
 * Controller for performing operations with news entity
 */
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Validated
@ControllerLog
public class NewsController {

    private final NewsService newsService;
    private final UsersService usersService;

    /**
     * Get all existing news and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of news DTO
     */
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNewsWithPagination(Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllNewsWithPagination(pageable));
    }

    /**
     * Get all news by request params and return them according to chosen size and page
     * @param dto value with fields for search
     * @param pageable page and maximum size of returning collections
     * @return list of founded news DTO
     */
    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllSearchedNewsWithPagination(dto, pageable));
    }

    /**
     * Get news with specified ID
     * @param id ID to search
     * @return news DTO with specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    /**
     * Get news with specified ID and all its comments
     * @param id ID to search
     * @param pageable page and maximum size of returning comments collections
     * @return news DTO with specified ID and its comments DTO
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<NewsWithCommentsDto> getNewsByIdWithCommentsPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, Pageable pageable) {
        return ResponseEntity.ok(newsService.getNewsByIdWithCommentsPagination(id, pageable));
    }

    /**
     * Add new news to repository
     * @param token token for user authentication
     * @param dto news DTO to add
     * @return response with created ID
     */
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

    /**
     * Update news with specified ID to values that contain DTO
     * @param id ID to update
     * @param token token for user authentication
     * @param dto DTO with values to update
     * @return response with updated ID
     */
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

    /**
     * Delete news with specified ID
     * @param id ID to delete
     * @param token token for user authentication
     * @return response with deleted ID
     */
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
