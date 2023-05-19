package ru.clevertec.news.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.news.dao.NewsRepository;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.News;
import ru.clevertec.news.models.enums.Operation;
import ru.clevertec.news.services.CommentsService;
import ru.clevertec.news.utils.mappers.NewsMapper;

import java.util.List;
import java.util.Optional;

import static generators.factories.AuthenticatedUserFactory.*;
import static generators.factories.PageFactory.getPageWith2News;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.comments.CommentDtoFactory.getAllCommentsForNews1;
import static generators.factories.news.ModificationNewsDtoFactory.getModificationDto;
import static generators.factories.news.NewsDtoFactory.*;
import static generators.factories.news.NewsFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

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
    void checkGetAllNewsWithPaginationShouldReturnAllNews() {
        doReturn(getPageWith2News()).when(repository).findAll(any(Pageable.class));
        doReturn(getFirst2NewsDtos()).when(mapper).convertAllNewsToDto(anyList());

        List<NewsDto> actualNews = service.getAllNewsWithPagination(getDefaultPageable());
        List<NewsDto> expectedNews = getFirst2NewsDtos();

        assertThat(actualNews).isEqualTo(expectedNews);

        verify(repository).findAll(any(Pageable.class));
        verify(mapper).convertAllNewsToDto(anyList());
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByTitleIgnoreCaseShouldReturn2News() {
        doReturn(getNews1()).when(mapper).convertDtoToNews(any(NewsDto.class));
        doReturn(getPageWith2News()).when(repository).findAll(any(Example.class), any(Pageable.class));
        doReturn(getFirst2NewsDtos()).when(mapper).convertAllNewsToDto(anyList());

        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByTitleIgnoreCase(), getDefaultPageable());
        List<NewsDto> expectedNews = getFirst2NewsDtos();

        assertThat(actualNews).isEqualTo(expectedNews);

        verify(mapper).convertDtoToNews(any(NewsDto.class));
        verify(repository).findAll(any(Example.class), any(Pageable.class));
        verify(mapper).convertAllNewsToDto(anyList());
    }

    @Test
    void checkGetNewsByIdShouldReturnNews1() {
        doReturn(Optional.of(getNews1())).when(repository).findById(anyLong());
        doReturn(getNewsDto1()).when(mapper).convertNewsToDto(any(News.class));

        NewsDto actualNews = service.getNewsById(1L);
        NewsDto expectedNews = getNewsDto1();

        assertThat(actualNews).isEqualTo(expectedNews);

        verify(repository).findById(anyLong());
        verify(mapper).convertNewsToDto(any(News.class));
    }

    @Test
    void checkGetNewsByIdShouldReturnNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.getNewsById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.GET.getName());

        verify(repository).findById(anyLong());
        verify(mapper, never()).convertNewsToDto(any(News.class));
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWithComments() {
        doReturn(Optional.of(getNews1())).when(repository).findById(anyLong());
        doReturn(getAllCommentsForNews1()).when(commentsService).getCommentsByNewsId(anyLong(), any(Pageable.class));
        doReturn(getNewsDto1WithComments()).when(mapper).convertNewsToDtoWithComments(any(News.class), anyList());

        NewsWithCommentsDto actualNews = service.getNewsByIdWithCommentsPagination(3L, getDefaultPageable());
        NewsWithCommentsDto expectedNews = getNewsDto1WithComments();

        assertThat(actualNews).isEqualTo(expectedNews);

        verify(repository).findById(anyLong());
        verify(commentsService).getCommentsByNewsId(anyLong(), any(Pageable.class));
        verify(mapper).convertNewsToDtoWithComments(any(News.class), anyList());
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.getNewsByIdWithCommentsPagination(100L, getDefaultPageable()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.GET.getName());

        verify(repository).findById(anyLong());
        verify(mapper, never()).convertNewsToDto(any(News.class));
    }

    @Test
    void checkAddNewsShouldReturnCreatedDto() {
        doReturn(getNews1()).when(mapper).convertModificationDtoToNews(any(ModificationNewsDto.class), anyString());
        doReturn(getNews1()).when(repository).save(any(News.class));
        doReturn( getCreatedNewsDto()).when(mapper).convertNewsToDto(any(News.class));

        NewsDto actualNews = service.addNews(getModificationDto(), getAdmin());
        NewsDto expectedNews = getCreatedNewsDto();

        assertThat(actualNews.getId()).isEqualTo(expectedNews.getId());
        assertThat(actualNews.getTitle()).isEqualTo(expectedNews.getTitle());
        assertThat(actualNews.getText()).isEqualTo(expectedNews.getText());
        assertThat(actualNews.getUsername()).isEqualTo(expectedNews.getUsername());

        verify(repository).save(any(News.class));
        verify(mapper).convertModificationDtoToNews(any(ModificationNewsDto.class), anyString());
        verify(mapper).convertNewsToDto(any(News.class));
    }

    @Test
    void checkAddNewsByShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.addNews(getModificationDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.ADD.getName());

        verify(repository, never()).save(any(News.class));
        verify(mapper, never()).convertModificationDtoToNews(any(ModificationNewsDto.class), anyString());
        verify(mapper, never()).convertNewsToDto(any(News.class));
    }

    @Test
    void checkUpdateNewsShouldReturnUpdatedDto() {
        doReturn(Optional.of(getNews1())).when(repository).findById(anyLong());
        doReturn(getNews1()).when(mapper).updateNews(any(News.class), any(ModificationNewsDto.class));
        doReturn(getUpdatedNewsDto()).when(mapper).convertNewsToDto(any(News.class));

        NewsDto actualNews = service.updateNews(1L, getModificationDto(), getAdmin());
        NewsDto expectedNews = getUpdatedNewsDto();

        assertThat(actualNews).isEqualTo(expectedNews);

        verify(repository).findById(anyLong());
        verify(mapper).updateNews(any(News.class), any(ModificationNewsDto.class));
        verify(mapper).convertNewsToDto(any(News.class));
    }

    @Test
    void checkUpdateNewsShouldThrowExceptionNotOwner() {
        doReturn(Optional.of(getNews2())).when(repository).findById(anyLong());
        assertThatThrownBy(() -> service.updateNews(3L, getModificationDto(), getJournalist()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_NEWS_OWNER + CANNOT_END + Operation.UPDATE.getName());

        verify(repository).findById(anyLong());
        verify(mapper, never()).updateNews(any(News.class), any(ModificationNewsDto.class));
        verify(mapper, never()).convertNewsToDto(any(News.class));
    }

    @Test
    void checkUpdateNewsShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.updateNews(3L, getModificationDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.UPDATE.getName());

        verify(repository, never()).findById(anyLong());
        verify(mapper, never()).updateNews(any(News.class), any(ModificationNewsDto.class));
        verify(mapper, never()).convertNewsToDto(any(News.class));
    }

    @Test
    void checkDeleteNewsByIdShouldBeCalledWithNews() {
        News expectedDeletedNews = getNews3WithComments();

        ArgumentCaptor<News> captor = ArgumentCaptor.forClass(News.class);
        doReturn(Optional.of(getNews3WithComments())).when(repository).findById(anyLong());
        doNothing().when(commentsService).deleteCommentsByNewsId(anyLong());
        service.deleteNewsById(1L, getAdmin());
        verify(repository).delete(captor.capture());

        News actualDeletedNews = captor.getValue();

        assertThat(actualDeletedNews).isEqualTo(expectedDeletedNews);

        verify(repository).findById(anyLong());
        verify(commentsService).deleteCommentsByNewsId(anyLong());
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.deleteNewsById(100L, getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.DELETE.getName());

        verify(repository).findById(anyLong());
        verify(repository, never()).delete(any(News.class));
        verify(commentsService, never()).deleteCommentsByNewsId(anyLong());
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNotOwnerException() {
        doReturn(Optional.of(getNews3WithComments())).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.deleteNewsById(3L, getJournalist()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_NEWS_OWNER + CANNOT_END + Operation.DELETE.getName());

        verify(repository).findById(anyLong());
        verify(repository, never()).delete(any(News.class));
        verify(commentsService, never()).deleteCommentsByNewsId(anyLong());
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.deleteNewsById(3L, getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.DELETE.getName());

        verify(repository, never()).findById(anyLong());
        verify(repository, never()).delete(any(News.class));
        verify(commentsService, never()).deleteCommentsByNewsId(anyLong());
    }
}