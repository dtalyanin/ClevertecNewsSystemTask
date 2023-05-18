package ru.clevertec.news.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.news.clients.dto.Permission;
import ru.clevertec.news.dao.NewsRepository;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;
import ru.clevertec.news.models.enums.Operation;
import ru.clevertec.news.services.CommentsService;
import ru.clevertec.news.services.NewsService;
import ru.clevertec.news.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.news.utils.PageableHelper.setPageableUnsorted;
import static ru.clevertec.news.utils.SearchHelper.getExample;
import static ru.clevertec.news.utils.UserHelper.*;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

/**
 * Service for performing CRUD operation with news in DB
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final CommentsService commentsService;

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getAllNewsWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<News> news = repository.findAll(pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        News newsWithFieldsToSearch = mapper.convertDtoToNews(dto);
        Example<News> example = getExample(newsWithFieldsToSearch);
        List<News> news = repository.findAll(example, pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "news")
    public NewsDto getNewsById(long id) {
        News news = getNewsByIdIfExist(id, Operation.GET);
        return mapper.convertNewsToDto(news);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable) {
        News news = getNewsByIdIfExist(id, Operation.GET);
        List<CommentDto> comments = commentsService.getCommentsByNewsId(id, pageable);
        return mapper.convertNewsToDtoWithComments(news, comments);
    }

    @Override
    @CachePut(value = "news", key = "#result.id")
    public NewsDto addNews(ModificationNewsDto dto, AuthenticatedUser user) {
        checkUserHasPermission(user, Operation.ADD);
        News news = mapper.convertModificationDtoToNews(dto, user.getUsername());
        repository.save(news);
        return mapper.convertNewsToDto(news);
    }

    @Override
    @CachePut(value = "news", key = "#result.id")
    public NewsDto updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.UPDATE);
        mapper.updateNews(news, dto);
        repository.save(news);
        return mapper.convertNewsToDto(news);
    }

    @Override
    @CacheEvict(value = "news", key = "#id")
    public void deleteNewsById(long id, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.DELETE);
        List<Long> commentsIds = news.getComments().stream().map(Comment::getId).toList();
        commentsService.deleteCommentsByNewsId(news.getId());
        repository.delete(news);
        commentsIds.forEach(commentsService::triggerCacheEvict);
    }

    /**
     * Get news by ID from DB
     * @param id ID for getting news
     * @param operation type of operation for performing
     * @return news with specified ID
     */
    private News getNewsByIdIfExist(long id, Operation operation) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            String message = NEWS_WITH_ID_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, id, ErrorCode.NEWS_NOT_FOUND);
        }
        return oNews.get();
    }

    /**
     * Get news by ID from DB and check user permissions for performing operation
     * @param id ID for getting news
     * @param user authenticated user to check permissions
     * @param operation type of operation for performing
     * @return news with specified ID
     */
    private News getNewsAndVerifyUserPermissions(long id, AuthenticatedUser user, Operation operation) {
        checkUserHasPermission(user, operation);
        News news = getNewsByIdIfExist(id, operation);
        checkUserIsNewsOwner(user, news, operation);
        return news;
    }

    /**
     * Check user permissions for performing operation with news
     * @param user authenticated user to check permissions
     * @param operation type of operation for performing
     */
    private void checkUserHasPermission(AuthenticatedUser user, Operation operation) {
        if (checkUserHasNotPermission(user, Permission.NEWS_MANAGE)) {
            String message = NOT_PERMISSIONS + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NO_PERMISSIONS_FOR_NEWS_MODIFICATION);
        }
    }
    /**
     * Check user has access to perform operation because it is owner or admin
     * @param user authenticated user to check
     * @param news news for performing operation
     * @param operation type of operation for performing
     */
    private void checkUserIsNewsOwner(AuthenticatedUser user, News news, Operation operation) {
        if (checkUserIsNotOwner(user, news.getUsername())) {
            String message = NOT_NEWS_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_NEWS_MODIFICATION);
        }
    }
}
