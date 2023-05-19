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
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.models.responses.ModificationResponse;
import ru.clevertec.news.services.CommentsService;

import java.util.List;

import static generators.factories.AuthenticatedUserFactory.getAdmin;
import static generators.factories.BearerTokenFactory.getAdminToken;
import static generators.factories.BearerTokenFactory.getEmptyToken;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.comments.CommentDtoFactory.*;
import static generators.factories.comments.CreateCommentDtoFactory.getCreateDto;
import static generators.factories.comments.UpdateCommentDtoFactory.getUpdateDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommentsControllerTest {

    @InjectMocks
    private CommentsController controller;
    @Mock
    private CommentsService commentsService;
    @Mock
    private UsersService usersService;

    @Test
    void checkGetAllCommentsWithPaginationShouldReturnAllNews() {
        doReturn(getAllComments()).when(commentsService).getCommentsWithPagination(any(Pageable.class));

        ResponseEntity<List<CommentDto>> actualResponse = controller.getCommentsWithPagination(getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        List<CommentDto> actualBody = actualResponse.getBody();

        List<CommentDto> expectedBody = getAllComments();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(commentsService).getCommentsWithPagination(any(Pageable.class));
    }

    @Test
    void checkGetAllSearchedCommentsWithPagination() {
        doReturn(getFirst2CommentsDto())
                .when(commentsService).getAllSearchedCommentsWithPagination(any(CommentDto.class), any(Pageable.class));

        ResponseEntity<List<CommentDto>> actualResponse =
                controller.getAllSearchedCommentsWithPagination(getDtoToSearchByDate(), getDefaultPageable());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        List<CommentDto> actualBody = actualResponse.getBody();

        List<CommentDto> expectedBody = getFirst2CommentsDto();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(commentsService).getAllSearchedCommentsWithPagination(any(CommentDto.class), any(Pageable.class));
    }

    @Test
    void checkGetCommentByIdShouldReturnComment1() {
        doReturn(getCommentDto1FromUser()).when(commentsService).getCommentById(anyLong());

        ResponseEntity<CommentDto> actualResponse = controller.getCommentById(1L);
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        CommentDto actualBody = actualResponse.getBody();

        CommentDto  expectedBody = getCommentDto1FromUser();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(commentsService).getCommentById(anyLong());
    }

    @Test
    void checkAddCommentShouldReturnResponseWithGeneratedId() {
        mockStatic(ServletUriComponentsBuilder.class);
        ServletUriComponentsBuilder servletBuilder = mock(ServletUriComponentsBuilder.class);
        when(ServletUriComponentsBuilder.fromCurrentRequest()).thenReturn(servletBuilder);
        doReturn(UriComponentsBuilder.fromPath("")).when(servletBuilder).path(anyString());

        doReturn(getCreatedCommentDto()).when(commentsService)
                .addComment(any(CreateCommentDto.class), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());

        ResponseEntity<ModificationResponse> actualResponse =
                controller.addComment(getAdminToken(), getCreateDto());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();
        HttpHeaders actualHeaders = actualResponse.getHeaders();

        ModificationResponse expectedBody = getCommentAddedResponse();
        HttpStatus expectedCode = HttpStatus.CREATED;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode),
                () -> assertThat(actualHeaders).containsKey(HttpHeaders.LOCATION));

        verify(usersService).getUserByUsername(anyString());
        verify(commentsService).addComment(any(CreateCommentDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkAddCommentShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.addComment(getEmptyToken(), getCreateDto()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(commentsService, never()).addComment(any(CreateCommentDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkUpdateCommentShouldReturnReturnResponseWithUpdatedId() {
        doReturn(getCommentDto1WithUpdatedText()).when(commentsService)
                .updateComment(anyLong(), any(UpdateCommentDto.class), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());
        ResponseEntity<ModificationResponse> actualResponse =
                controller.updateComment(1L, getAdminToken(), getUpdateDto());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getCommentUpdatedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(usersService).getUserByUsername(anyString());
        verify(commentsService).updateComment(anyLong(), any(UpdateCommentDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkUpdateCommentShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.updateComment(1L, getEmptyToken(), getUpdateDto()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(commentsService, never()).updateComment(anyLong(), any(UpdateCommentDto.class), any(AuthenticatedUser.class));
    }

    @Test
    void checkDeleteCommentByIdShouldReturnResponseWithDeletedId() {
        doNothing().when(commentsService).deleteCommentById(anyLong(), any(AuthenticatedUser.class));
        doReturn(getAdmin()).when(usersService).getUserByUsername(anyString());

        ResponseEntity<ModificationResponse> actualResponse = controller.deleteCommentById(6L, getAdminToken());
        HttpStatusCode actualCode = actualResponse.getStatusCode();
        ModificationResponse actualBody = actualResponse.getBody();

        ModificationResponse expectedBody = getCommentDeletedResponse();
        HttpStatus expectedCode = HttpStatus.OK;

        assertAll(
                () -> assertThat(actualBody).isEqualTo(expectedBody),
                () -> assertThat(actualCode).isEqualTo(expectedCode));

        verify(usersService).getUserByUsername(anyString());
        verify(commentsService).deleteCommentById(anyLong(), any(AuthenticatedUser.class));
    }

    @Test
    void checkDeleteCommentByIdShouldThrowTokenException() {
        assertThatThrownBy(() -> controller.deleteCommentById(1L, getEmptyToken()))
                .isInstanceOf(TokenException.class);

        verify(usersService, never()).getUserByUsername(anyString());
        verify(commentsService, never()).deleteCommentById(anyLong(), any(AuthenticatedUser.class));
    }
}