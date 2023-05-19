package ru.clevertec.news.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.clevertec.exceptions.exceptions.TokenException;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.clients.services.UsersService;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.responses.ModificationResponse;
import ru.clevertec.news.services.NewsService;

import java.util.List;

import static generators.factories.AuthenticatedUserFactory.getAdmin;
import static generators.factories.BearerTokenFactory.getAdminToken;
import static generators.factories.BearerTokenFactory.getEmptyToken;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.news.ModificationNewsDtoFactory.getModificationDto;
import static generators.factories.news.NewsDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsControllerTest {

    @InjectMocks
    private NewsController controller;
    @Mock
    private UsersService usersService;
    @Mock
    private NewsService newsService;

    @Test
    void checkGetAllNewsWithPaginationShouldReturnAllNews() {
        doReturn(getAllNewsDtos())
                .when(newsService).getAllNewsWithPagination(any(Pageable.class));

        ResponseEntity<List<NewsDto>> actualResponse = controller.getAllNewsWithPagination(getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        List<NewsDto> actualBody = actualResponse.getBody();

        List<NewsDto> expectedBody = getAllNewsDtos();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(newsService).getAllNewsWithPagination(any(Pageable.class));
    }

    @Test
    void checkGetAllSearchedNewsWithPagination() {
        doReturn(getFirst2NewsDtos())
                .when(newsService).getAllSearchedNewsWithPagination(any(NewsDto.class), any(Pageable.class));

        ResponseEntity<List<NewsDto>> actualResponse =
                controller.getAllSearchedNewsWithPagination(getDtoToSearchByTitleIgnoreCase(), getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        List<NewsDto> actualBody = actualResponse.getBody();

        List<NewsDto> expectedBody = getFirst2NewsDtos();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(newsService).getAllSearchedNewsWithPagination(any(NewsDto.class), any(Pageable.class));
    }

    @Test
    void checkGetNewsByIdShouldReturnNews1() {
        doReturn(getNewsDto1()).when(newsService).getNewsById(anyLong());

        ResponseEntity<NewsDto> actualResponse = controller.getNewsById(1L);
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        NewsDto actualBody = actualResponse.getBody();

        NewsDto  expectedBody = getNewsDto1();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(newsService).getNewsById(anyLong());
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWithComments() {
        doReturn(getNewsDto1WithComments()).when(newsService)
                .getNewsByIdWithCommentsPagination(anyLong(), any(Pageable.class));

        ResponseEntity<NewsWithCommentsDto> actualResponse =
                controller.getNewsByIdWithCommentsPagination(1L, getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        NewsWithCommentsDto actualBody = actualResponse.getBody();

        NewsWithCommentsDto  expectedBody = getNewsDto1WithComments();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(newsService).getNewsByIdWithCommentsPagination(anyLong(), any(Pageable.class));
    }

    @Test
    void checkAddNewsShouldReturnResponseWithGeneratedId() {
        mockStatic(ServletUriComponentsBuilder.class);
        ServletUriComponentsBuilder servletBuilder = mock(ServletUriComponentsBuilder.class);
        when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(servletBuilder);
        doReturn(UriComponentsBuilder.fromPath("")).when(servletBuilder).path(anyString());

        doReturn(getCreatedNewsDto()).when(newsService)
                .addNews(any(ModificationNewsDto.class), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());

        ResponseEntity<ModificationResponse> actualResponse =
                controller.addNews(getModificationDto(), getAdminToken());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();
        HttpHeaders actualHeaders = actualResponse.getHeaders();

        ModificationResponse expectedBody = getNewsAddedResponse();
        HttpStatus expectedCode = HttpStatus.CREATED;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode),
                () -> assertThat(actualHeaders).containsKey(HttpHeaders.LOCATION));

        verify(usersService).getUserByUsername(anyString());
        verify(newsService).addNews(any(ModificationNewsDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkAddNewsShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.addNews(getModificationDto(), getEmptyToken()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(newsService, never()).addNews(any(ModificationNewsDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkUpdateNewsShouldReturnReturnResponseWithUpdatedId() {
        doReturn(getUpdatedNewsDto()).when(newsService)
                .updateNews(anyLong(), any(ModificationNewsDto.class), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());

        ResponseEntity<ModificationResponse> actualResponse =
                controller.updateNews(1L, getAdminToken(), getModificationDto());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getNewsUpdatedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(usersService).getUserByUsername(anyString());
        verify(newsService).updateNews(anyLong(), any(ModificationNewsDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkUpdateNewsShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.updateNews(1L, getEmptyToken(), getModificationDto()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(newsService, never()).updateNews(anyLong(), any(ModificationNewsDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkDeleteNewsByIdShouldReturnResponseWithDeletedId() {
        doNothing().when(newsService).deleteNewsById(anyLong(), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());

        ResponseEntity<ModificationResponse> actualResponse = controller.deleteNewsById(1L, getAdminToken());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getNewsDeletedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(usersService).getUserByUsername(anyString());
        verify(newsService).deleteNewsById(anyLong(), any(AuthenticatedUser.class));
    }

    @Test
    void checkDeleteNewsByIdShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.deleteNewsById(1L, getEmptyToken()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(newsService, never()).deleteNewsById(anyLong(), any(AuthenticatedUser.class));
    }
}