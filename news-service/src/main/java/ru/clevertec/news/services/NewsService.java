package ru.clevertec.news.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

import java.util.List;

/**
 * Service for performing CRUD operation with news
 */
public interface NewsService {
    /**
     * Get all existing news and return them according to chosen size and page
     * @param pageable page and maximum size of returning collections
     * @return list of news DTO
     */
    List<NewsDto> getAllNewsWithPagination(Pageable pageable);

    /**
     * Get all news by request params and return them according to chosen size and page
     * @param dto value with fields for search
     * @param pageable page and maximum size of returning collections
     * @return list of founded news DTO
     */
    List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable);

    /**
     * Get news with specified ID
     * @param id ID to search
     * @return news DTO with specified ID
     */
    NewsDto getNewsById(long id);

    /**
     * Get news with specified ID and all its comments
     * @param id ID to search
     * @param pageable page and maximum size of returning comments collections
     * @return news DTO with specified ID and its comments DTO
     */
    NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable);

    /**
     * Add new news to repository
     * @param user authenticated user who performs operation
     * @param dto news DTO to add
     * @return created news DTO
     */
    NewsDto addNews(ModificationNewsDto dto, AuthenticatedUser user);

    /**
     * Update news with specified ID to values that contain DTO
     * @param id ID to update
     * @param user authenticated user who performs operation
     * @param dto DTO with values to update
     * @return updated comment DTO
     */
    NewsDto updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user);

    /**
     * Delete news with specified ID
     * @param id ID to delete
     * @param user authenticated user who performs operation
     */
    void deleteNewsById(long id, AuthenticatedUser user);
}
