package ru.clevertec.news.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.news.dao.NewsRepository;
import ru.clevertec.news.services.CommentsService;
import ru.clevertec.news.utils.mappers.NewsMapper;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @InjectMocks
    private NewsServiceImpl service;
    @Mock
    private NewsRepository repository;
    @Mock
    private NewsMapper mapper;
    @Mock
    private CommentsService commentsService;

    @Test
    void getAllNewsWithPagination() {
    }

    @Test
    void getAllSearchedNewsWithPagination() {
    }

    @Test
    void getNewsById() {
    }

    @Test
    void getNewsByIdWithCommentsPagination() {
    }

    @Test
    void addNews() {
    }

    @Test
    void updateNews() {
    }

    @Test
    void deleteNewsById() {
    }
}