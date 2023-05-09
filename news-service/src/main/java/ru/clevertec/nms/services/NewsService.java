package ru.clevertec.nms.services;

import org.springframework.data.domain.Pageable;
import ru.clevertec.nms.dto.news.ModificationNewsDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Operation;

import java.util.List;

public interface NewsService {
    List<NewsDto> getAllNewsWithPagination(Pageable pageable);
    List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable);
    NewsDto getNewsById(long id);
    NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable);
    void checkNewsWithIdNotExist(long id, Operation operation);
    NewsDto addNews(ModificationNewsDto dto, AuthenticatedUser user);
    NewsDto updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user);
    void deleteNewsById(long id, AuthenticatedUser user);
}
