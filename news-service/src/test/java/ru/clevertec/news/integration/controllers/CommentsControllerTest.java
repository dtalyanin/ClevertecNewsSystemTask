package ru.clevertec.news.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.news.integration.BaseIntegrationTest;

import static generators.factories.BearerTokenFactory.getAdminToken;
import static generators.factories.BearerTokenFactory.getSubscriberToken;
import static generators.factories.ErrorResponseFactory.*;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.ValidationResponseFactory.getResponseEmptyCommentText;
import static generators.factories.ValidationResponseFactory.getResponseNotCorrectId;
import static generators.factories.comments.CommentDtoFactory.*;
import static generators.factories.comments.CreateCommentDtoFactory.*;
import static generators.factories.comments.UpdateCommentDtoFactory.getUpdateDto;
import static generators.factories.comments.UpdateCommentDtoFactory.getUpdateDtoWithEmptyText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class CommentsControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    void checkGetCommentsWithPaginationShouldReturnAll6Comments() {
        String jsonComments = mapper.writeValueAsString(getAllComments());

        mvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void checkGetCommentsWithPaginationShouldReturn2CommentsWithPageSize2() {
        String jsonComments = mapper.writeValueAsString(getFirst2CommentsDto());

        mvc.perform(get("/comments")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetCommentsWithPaginationShouldReturn2CommentsFrom2PageAndSize2() {
        String jsonComments = mapper.writeValueAsString(getFrom2PageFirst2Comments());

        mvc.perform(get("/comments")
                        .param("size", "2")
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetCommentsWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        String jsonComments = mapper.writeValueAsString(getCommentsDtoEmptyList());

        mvc.perform(get("/comments")
                        .param("size", "10")
                        .param("page", "20"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetCommentsWithPaginationShouldReturnAll6CommentsWhenNegativeSize() {
        String jsonComments = mapper.writeValueAsString(getAllComments());

        mvc.perform(get("/comments")
                        .param("size", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationByTextIgnoreCaseShouldReturn2Comments() {
        String jsonComments = mapper.writeValueAsString(getSearchedByText());

        mvc.perform(get("/comments/search")
                        .param("text", "coMMent 2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationByTextIgnoreCaseShouldReturn1CommentsWithSize1() {
        String jsonComments = mapper.writeValueAsString(getSearchedByTextWithSize1());

        mvc.perform(get("/comments/search")
                        .param("text", "coMMent 2")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationByUsernameIgnoreCaseShouldReturn2Comments() {
        String jsonComments = mapper.writeValueAsString(getSearchedByUsername());

        mvc.perform(get("/comments/search")
                        .param("username", "uSeR 2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationByTextAndUsernameIgnoreCaseShouldReturn1Comment() {
        String jsonComments = mapper.writeValueAsString(getSearchedByUsernameAndText());

        mvc.perform(get("/comments/search")
                        .param("text", "Comment")
                        .param("username", "User 3"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationByDateShouldReturn1Comment() {
        String jsonComments = mapper.writeValueAsString( getSearchedByDate());

        mvc.perform(get("/comments/search")
                        .param("time", "2023-02-01T12:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListUsernameNotExist() {
        String jsonComments = mapper.writeValueAsString(getCommentsDtoEmptyList());

        mvc.perform(get("/comments/search")
                        .param("username", "User not exist"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListTextNotExist() {
        String jsonComments = mapper.writeValueAsString(getCommentsDtoEmptyList());

        mvc.perform(get("/comments/search")
                        .param("text", "Text not exist"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListDateNotExist() {
        String jsonComments = mapper.writeValueAsString(getCommentsDtoEmptyList());

        mvc.perform(get("/comments/search")
                        .param("time", "2022-02-01T12:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedCommentsWithPaginationShouldIgnoreId() {
        String jsonComments = mapper.writeValueAsString(getAllComments());

        mvc.perform(get("/comments/search")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComments))
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    @SneakyThrows
    void checkGetCommentByIdShouldReturnComment1() {
        String jsonComment = mapper.writeValueAsString(getCommentDto1FromUser());

        mvc.perform(get("/comments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonComment));
    }

    @Test
    @SneakyThrows
    void checkGetCommentByIdShouldReturnNotFoundException() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithCommentNotFoundExceptionForGet());

        mvc.perform(get("/comments/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddCommentShouldResponseWithCreatedId() {
        String jsonModResponse = mapper.writeValueAsString(getCommentAddedResponse());
        String jsonCreateDto = mapper.writeValueAsString(getCreateDto());

        mvc.perform(post("/comments")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonModResponse))
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("comments/7")));
    }

    @Test
    @SneakyThrows
    void checkAddCommentShouldReturnResponseEmptyText() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyCommentText());
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithEmptyText());

        mvc.perform(post("/comments")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddCommentShouldReturnResponseNewsNotFound() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNewsNotFoundExceptionForAdd());
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithNotExistingNewsId());

        mvc.perform(post("/comments")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddCommentShouldReturnResponseForbiddenWhenNoToken() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoAuthHeader());
        String jsonCreateDto = mapper.writeValueAsString(getCreateDto());

        mvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateCommentShouldReturnShouldReturnResponseWithUpdatedId() {
        String jsonModResponse = mapper.writeValueAsString(getCommentUpdatedResponse());
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());

        mvc.perform(patch("/comments/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateCommentShouldReturnResponseNotCorrectId() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseNotCorrectId());
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());

        mvc.perform(patch("/comments/{id}", -1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateCommentShouldReturnResponseEmptyText() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyCommentText());
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDtoWithEmptyText());

        mvc.perform(patch("/comments/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateCommentShouldThrowExceptionNotOwner() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNotOwnerCommentForUpdate());
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());

        mvc.perform(patch("/comments/{id}", 6L)
                        .header(HttpHeaders.AUTHORIZATION, getSubscriberToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateCommentShouldReturnResponseForbiddenWhenNoToken() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoAuthHeader());
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());

        mvc.perform(patch("/comments/{id}", 6L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteCommentByIdShouldReturnResponseWithDeletedId() {
        String jsonModResponse = mapper.writeValueAsString(getCommentDeletedResponse());

        mvc.perform(delete("/comments/{id}", 6L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteCommentByIdShouldReturnResponseNotCorrectId() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseNotCorrectId());

        mvc.perform(delete("/comments/{id}", -1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteCommentByIdShouldReturnResponseNotFound() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithCommentNotFoundExceptionForDelete());

        mvc.perform(delete("/comments/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteCommentByIdShouldReturnResponseNotOwner() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNotOwnerCommentForDelete());

        mvc.perform(delete("/comments/{id}", 6L)
                        .header(HttpHeaders.AUTHORIZATION, getSubscriberToken()))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteCommentByIdShouldReturnResponseForbiddenWhenNoToken() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoAuthHeader());

        mvc.perform(delete("/comments/{id}", 1L))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }
}