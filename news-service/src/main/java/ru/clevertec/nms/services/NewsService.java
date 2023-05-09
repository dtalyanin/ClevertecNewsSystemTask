package ru.clevertec.nms.services;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.news.ModificationNewsDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.models.responses.ModificationResponse;

import java.util.List;

public interface NewsService {
    @Transactional(readOnly = true)
    List<NewsDto> getAllNewsWithPagination(Pageable pageable);

    @Transactional(readOnly = true)
    List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable);

    @Transactional(readOnly = true)
    NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable);

    @Transactional(readOnly = true)
    void checkNewsWithIdNotExist(long id, Operation operation);

    ModificationResponse addNews(ModificationNewsDto dto, AuthenticatedUser user);

    ModificationResponse updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user);

    ModificationResponse deleteNewsById(long id, AuthenticatedUser user);
}
