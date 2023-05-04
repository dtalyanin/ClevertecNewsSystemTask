package ru.clevertec.nms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.clients.dto.Permission;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.dto.news.SearchNewsDto;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.security.UserDetailsDecorator;
import ru.clevertec.nms.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.*;
import static ru.clevertec.nms.utils.SearchHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
public class NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final CommentsService commentsService;

    @Autowired
    public NewsService(NewsRepository repository, NewsMapper mapper, CommentsService commentsService) {
        this.repository = repository;
        this.mapper = mapper;
        this.commentsService = commentsService;
    }

    @Transactional(readOnly = true)
    public List<NewsDto> getAllNewsWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<News> news = repository.findAll(pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Transactional(readOnly = true)
    public List<NewsDto> getAllSearchedNewsWithPagination(SearchNewsDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        News newsWithFieldsToSearch = mapper.convertSearchDtoToNews(dto);
        Example<News> example = getExample(newsWithFieldsToSearch);
        List<News> news = repository.findAll(example, pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Transactional(readOnly = true)
    public NewsWithCommentsDto getNewsWithCommentsPagination(long id, Pageable pageable) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        List<CommentDto> comments = commentsService.getCommentsByNewsIdWithPagination(id, pageable);
        return mapper.convertNewsToDtoWithComments(news, comments);
    }

    public ModificationResponse addNews(NewsDto dto, UserDetailsDecorator userDetails) {
        if (!verifyUserHasPermissions(userDetails)) {
            throw new AccessException(NOT_ENOUGH_PERMISSIONS + CANNOT_ADD_END,
                    userDetails.getUser().getRole(),
                    ErrorCode.NO_PERMISSIONS_FOR_MODIFICATION_NEWS);
        }
        News news = mapper.convertDtoToNews(dto);
        repository.save(news);
        return new ModificationResponse(news.getId(),NEWS_ADDED);
    }

    public ModificationResponse updateNews(long id, NewsDto dto, UserDetailsDecorator userDetails) {
        if (verifyUserHasPermissions(userDetails)) {
            throw new AccessException(NOT_ENOUGH_PERMISSIONS + CANNOT_UPDATE_END,
                    userDetails.getUser().getRole(),
                    ErrorCode.NO_PERMISSIONS_FOR_MODIFICATION_NEWS);
        }
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND + CANNOT_UPDATE_END, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        if (verifyUserIsNewsOwner(userDetails, news)) {
            throw new AccessException(NOT_NEWS_OWNER + CANNOT_UPDATE_END,
                    userDetails.getUser().getRole(),
                    ErrorCode.NO_PERMISSIONS_FOR_MODIFICATION_NEWS);
        }
        mapper.updateNews(news, dto);
        repository.save(news);
        return new ModificationResponse(id, NEWS_UPDATED);
    }

    public ModificationResponse deleteNewsById(long id, UserDetailsDecorator userDetails) {
        if (verifyUserHasPermissions(userDetails)) {
            throw new AccessException(NOT_ENOUGH_PERMISSIONS + CANNOT_DELETE_END,
                    userDetails.getUser().getRole(),
                    ErrorCode.NO_PERMISSIONS_FOR_MODIFICATION_NEWS);
        }
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND + CANNOT_DELETE_END, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        if (verifyUserIsNewsOwner(userDetails, news)) {
            throw new AccessException(NOT_NEWS_OWNER + CANNOT_DELETE_END,
                    userDetails.getUser().getRole(),
                    ErrorCode.NO_PERMISSIONS_FOR_MODIFICATION_NEWS);
        }
        repository.delete(news);
        return new ModificationResponse(id, NEWS_DELETED);
    }

    private boolean verifyUserHasPermissions(UserDetailsDecorator userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .noneMatch(Permission.NEWS_MANAGE.name()::equals);
    }

    private boolean verifyUserIsNewsOwner(UserDetailsDecorator userDetails, News news) {
        return news.getUsername().equals(userDetails.getUsername());
    }
}
