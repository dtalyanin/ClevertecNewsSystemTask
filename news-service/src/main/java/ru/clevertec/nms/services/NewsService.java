package ru.clevertec.nms.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.nms.dao.NewsRepository;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.dto.news.SearchNewsDto;
import ru.clevertec.nms.exceptions.ErrorCode;
import ru.clevertec.nms.exceptions.NotFoundException;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;
import ru.clevertec.nms.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.nms.utils.PageableHelper.*;
import static ru.clevertec.nms.utils.SearchHelper.*;
import static ru.clevertec.nms.utils.constants.MessageConstants.NEWS;
import static ru.clevertec.nms.utils.constants.MessageConstants.NOT_FOUND;

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

    public List<NewsDto> getAllNewsWithPagination(Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        List<News> news = repository.findAll(pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    public List<NewsDto> getAllSearchedNewsWithPagination(SearchNewsDto dto, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        News newsWithFieldsToSearch = mapper.convertSearchDtoToNews(dto);
        Example<News> example = getExample(newsWithFieldsToSearch);
        List<News> news = repository.findAll(example, pageable).getContent();
        return mapper.convertAllNewsToDto(news);
    }

    public NewsWithCommentsDto getNewsWithCommentsPagination(long id, Pageable pageable) {
        Optional<News> oNews = repository.findById(id);
        if (oNews.isEmpty()) {
            throw new NotFoundException(NEWS + NOT_FOUND, id, ErrorCode.NEWS_NOT_FOUND);
        }
        News news = oNews.get();
        List<Comment> comments = commentsService.getCommentsByNewsIdWithPagination(id, pageable);
        return mapper.convertNewsToDtoWithComments(news, comments);
    }

}
