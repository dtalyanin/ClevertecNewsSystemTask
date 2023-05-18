package ru.clevertec.news.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

import java.util.List;

public interface NewsService {
    List<NewsDto> getAllNewsWithPagination(Pageable pageable);
    List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable);
    NewsDto getNewsById(long id);
    NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable);
    NewsDto addNews(ModificationNewsDto dto, AuthenticatedUser user);
    NewsDto updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user);
    void deleteNewsById(long id, AuthenticatedUser user);
}
