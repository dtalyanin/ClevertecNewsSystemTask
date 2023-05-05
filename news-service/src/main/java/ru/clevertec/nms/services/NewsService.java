package ru.clevertec.nms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.news.*;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.*;

import static ru.clevertec.nms.utils.SearchHelper.*;
import static ru.clevertec.nms.utils.UserHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final CommentsService commentsService;

    @Transactional(readOnly = true)
    public List<NewsDto> getAllNewsWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<News> news = repository.findAll(pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Transactional(readOnly = true)
    public List<NewsDto> getAllSearchedNewsWithPagination(NewsDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        News newsWithFieldsToSearch = mapper.convertDtoToNews(dto);
        Example<News> example = getExample(newsWithFieldsToSearch);
        List<News> news = repository.findAll(example, pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    @Transactional(readOnly = true)
    public NewsWithCommentsDto getNewsWithCommentsPagination(long id, Pageable pageable) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS_WITH_ID_NOT_FOUND + NOT_FOUND, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        List<CommentDto> comments = commentsService.getCommentsByNewsIdWithPagination(id, pageable);
        return mapper.convertNewsToDtoWithComments(news, comments);
    }

    public ModificationResponse addNews(ModificationNewsDto dto, AuthenticatedUser user) {
        News news = mapper.convertModificationDtoToNews(dto, user.getUsername());
        repository.save(news);
        return new ModificationResponse(news.getId(), NEWS_ADDED);
    }

    public ModificationResponse updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.UPDATE);
        mapper.updateNews(news, dto);
        repository.save(news);
        return new ModificationResponse(id, NEWS_UPDATED);
    }

    public ModificationResponse deleteNewsById(long id, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.DELETE);
        repository.delete(news);
        return new ModificationResponse(id, NEWS_DELETED);
    }

    private News getNewsAndVerifyUserPermissions(long newsId, AuthenticatedUser user, Operation operation) {
        Optional<News> oNews = repository.findById(newsId);
        if (oNews.isEmpty()) {
            String message = NEWS_WITH_ID_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, newsId, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        if (checkUserCannotPerformOperation(user, news.getUsername())) {
            String message = NOT_NEWS_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_MODIFICATION_NEWS);
        }
        return news;
    }
}
