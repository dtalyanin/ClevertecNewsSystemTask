package ru.clevertec.nms.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.news.ModificationNewsDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.exceptions.AccessException;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.AuthenticatedUser;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.models.Operation;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.services.NewsService;
import ru.clevertec.nms.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.setPageableUnsorted;
import static ru.clevertec.nms.utils.SearchHelper.getExample;
import static ru.clevertec.nms.utils.UserHelper.checkUserCannotPerformOperation;
import static ru.clevertec.nms.utils.constants.MessageConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final CommentsRepository commentsRepository;
    private final NewsMapper mapper;

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
    public NewsWithCommentsDto getNewsByIdWithCommentsPagination(long id, Pageable pageable) {
        News news = getNewsById(id, Operation.GET);
        List<Comment> comments = commentsRepository.findAllByNewsId(id, pageable);
        return mapper.convertNewsToDtoWithComments(news, comments);
    }

    @Override
    @Transactional(readOnly = true)
    public News getNewsById(long id, Operation operation) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            String message = NEWS_WITH_ID_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, id, ErrorCode.NEWS_NOT_FOUND);
        }
        return oNews.get();
    }

    @Override
    @Transactional(readOnly = true)
    public void checkNewsWithIdNotExist(long id, Operation operation) {
        if (!repository.existsById(id)) {
            String message = NEWS_WITH_ID_NOT_FOUND + CANNOT_END + operation.getName();
            throw new NotFoundException(message, id, ErrorCode.NEWS_NOT_FOUND);
        }
    }

    @Override
    public ModificationResponse addNews(ModificationNewsDto dto, AuthenticatedUser user) {
        News news = mapper.convertModificationDtoToNews(dto, user.getUsername());
        repository.save(news);
        return new ModificationResponse(news.getId(), NEWS_ADDED);
    }

    @Override
    public ModificationResponse updateNews(long id, ModificationNewsDto dto, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.UPDATE);
        mapper.updateNews(news, dto);
        repository.save(news);
        return new ModificationResponse(id, NEWS_UPDATED);
    }

    @Override
    public ModificationResponse deleteNewsById(long id, AuthenticatedUser user) {
        News news = getNewsAndVerifyUserPermissions(id, user, Operation.DELETE);
        repository.delete(news);
        return new ModificationResponse(id, NEWS_DELETED);
    }

    private News getNewsAndVerifyUserPermissions(long id, AuthenticatedUser user, Operation operation) {
        News news = getNewsById(id, operation);
        if (checkUserCannotPerformOperation(user, news.getUsername())) {
            String message = NOT_NEWS_OWNER + CANNOT_END + operation.getName();
            throw new AccessException(message, ErrorCode.NOT_OWNER_FOR_MODIFICATION_NEWS);
        }
        return news;
    }
}
