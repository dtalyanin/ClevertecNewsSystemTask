package ru.clevertec.nms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.clients.dto.Role;
import ru.clevertec.nms.clients.services.UsersService;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.news.*;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.setPageableUnsorted;
import static ru.clevertec.nms.utils.SearchHelper.getExample;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final CommentsService commentsService;
    private final UsersService usersService;

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

    public ModificationResponse addNews(CreateNewsDto dto, AuthenticatedUser user) {
        News news = mapper.convertCreateDtoToNews(dto);
        news.setUsername(user.getUsername());
        repository.save(news);
        return new ModificationResponse(news.getId(), NEWS_ADDED);
    }

    public ModificationResponse updateNews(long id, UpdateNewsDto dto, AuthenticatedUser user) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND + CANNOT_UPDATE_END, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        if (checkUserCannotPerformOperation(user, news)) {
            throw new AccessException(NOT_NEWS_OWNER + CANNOT_UPDATE_END,
                    ErrorCode.NOT_OWNER_FOR_MODIFICATION_NEWS);
        }
        if (usersService.getUserByUsername(dto.getUsername()).isEmpty()) {
            throw new NotFoundException(USERNAME_NOT_FOUND, dto.getUsername(), ErrorCode.USER_NOT_FOUND);
        }
        mapper.updateNews(news, dto);
        repository.save(news);
        return new ModificationResponse(id, NEWS_UPDATED);
    }

    public ModificationResponse deleteNewsById(long id, AuthenticatedUser user) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND + CANNOT_DELETE_END, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        if (checkUserCannotPerformOperation(user, news)) {
            throw new AccessException(NOT_NEWS_OWNER + CANNOT_DELETE_END,
                    ErrorCode.NOT_OWNER_FOR_MODIFICATION_NEWS);
        }
        repository.delete(news);
        return new ModificationResponse(id, NEWS_DELETED);
    }

    private boolean checkUserCannotPerformOperation(AuthenticatedUser user, News news) {
        return !checkUserIsAdmin(user) && !checkUserIsNewsOwner(user, news);
    }

    private boolean checkUserIsNewsOwner(AuthenticatedUser user, News news) {
        return news.getUsername().equals(user.getUsername());
    }

    private boolean checkUserIsAdmin(AuthenticatedUser user) {
        return user.getAuthorities().contains(Role.ADMIN.getNameWithRolePrefix());
    }
}
