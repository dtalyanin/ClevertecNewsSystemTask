package ru.clevertec.news.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import generators.factories.news.NewsDtoFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.news.integration.BaseIntegrationTest;

import static generators.factories.BearerTokenFactory.*;
import static generators.factories.AuthenticatedUserFactory.getSubscriber;
import static generators.factories.ErrorResponseFactory.*;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.ValidationResponseFactory.*;
import static generators.factories.news.ModificationNewsDtoFactory.*;
import static generators.factories.news.NewsDtoFactory.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class NewsControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @SneakyThrows
    void checkGetAllNewsWithPaginationShouldReturnAll3News() {
        String jsonNews = mapper.writeValueAsString(getAllNewsDtos());

        mvc.perform(get("/news"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsWithPaginationShouldReturn2NewsWithPageSize2() {
        String jsonNews = mapper.writeValueAsString(getFirst2NewsDtos());

        mvc.perform(get("/news")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetNeDwsWithPaginationShouldReturn2NewsFrom2PageAndSize2() {
        String jsonNews = mapper.writeValueAsString(getNewsDto3AsList());

        mvc.perform(get("/news")
                        .param("size", "2")
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        String jsonNews = mapper.writeValueAsString(NewsDtoFactory.getEmptyListNewsDtos());

        mvc.perform(get("/news")
                        .param("size", "20")
                        .param("page", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByTitleIgnoreCaseShouldReturn2News() {
        String jsonNews = mapper.writeValueAsString(getNews2And3Dtos());

        mvc.perform(get("/news/search")
                        .param("title", "nEwS "))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByTextIgnoreCaseShouldReturn2News() {
        String jsonNews = mapper.writeValueAsString(getNews2And3Dtos());

        mvc.perform(get("/news/search")
                        .param("text", "tExT "))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByTextIgnoreCaseShouldReturn1NewsWithSize1() {
        String jsonNews = mapper.writeValueAsString(getNewsDto2AsList());

        mvc.perform(get("/news/search")
                        .param("text", "tExT ")
                        .param("size", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByUsernameIgnoreCaseShouldReturn2News() {
        String jsonNews = mapper.writeValueAsString(getNews2And3Dtos());

        mvc.perform(get("/news/search")
                        .param("username", "uSeR "))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByTextAndUsernameIgnoreCaseShouldReturn1News() {
        String jsonNews = mapper.writeValueAsString(getNewsDto2AsList());

        mvc.perform(get("/news/search")
                        .param("username", "uSeR 2")
                        .param("text", "tExT 2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationByDateShouldReturn1News() {
        String jsonNews = mapper.writeValueAsString(getNewsDto3AsList());

        mvc.perform(get("/news/search")
                        .param("time", "2023-03-01T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListUsernameNotExist() {
        String jsonNews = mapper.writeValueAsString(getEmptyListNewsDtos());

        mvc.perform(get("/news/search")
                        .param("username", "User not exist"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListTextNotExist() {
        String jsonNews = mapper.writeValueAsString(getEmptyListNewsDtos());

        mvc.perform(get("/news/search")
                        .param("text", "Text not exist"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListDateNotExist() {
        String jsonNews = mapper.writeValueAsString(getEmptyListNewsDtos());

        mvc.perform(get("/news/search")
                        .param("time", "2022-03-01T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListTitleNotExist() {
        String jsonNews = mapper.writeValueAsString(getEmptyListNewsDtos());

        mvc.perform(get("/news/search")
                        .param("title", "Title not exist"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllSearchedNewsWithPaginationShouldIgnoreId() {
        String jsonNews = mapper.writeValueAsString(getAllNewsDtos());

        mvc.perform(get("/news/search")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdShouldReturnNews1() {
        String jsonNews = mapper.writeValueAsString(getNewsDto1());

        mvc.perform(get("/news/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdShouldReturnNotFoundException() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNewsNotFoundExceptionForGet());

        mvc.perform(get("/news/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWith4Comments() {
        String jsonNews = mapper.writeValueAsString(getNewsDto1WithComments());

        mvc.perform(get("/news/{id}/comments", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$.comments", hasSize(4)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWith2Comments() {
        String jsonNews = mapper.writeValueAsString(getNewsDto1With2Comments());

        mvc.perform(get("/news/{id}/comments", 1L)
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$.comments", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWithNoComments() {
        String jsonNews = mapper.writeValueAsString(getNewsDto3WithNoComments());

        mvc.perform(get("/news/{id}/comments", 3L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonNews))
                .andExpect(jsonPath("$.comments", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetNewsByIdWithCommentsPaginationShouldReturnResponseNotFound() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNewsNotFoundExceptionForGet());

        mvc.perform(get("/news/{id}/comments", 100L))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddNewsShouldReturnResponseWithCreatedId() {
        String jsonCreateDto = mapper.writeValueAsString(getModificationDto());
        String jsonModResponse = mapper.writeValueAsString(getNewsAddedResponse());

        mvc.perform(post("/news")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonModResponse))
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("news/4")));
    }

    @Test
    @SneakyThrows
    void checkAddNewsShouldReturnResponseEmptyTitle() {
        String jsonCreateDto = mapper.writeValueAsString(getModificationDtoWithEmptyTitle());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyNewsTitle());

        mvc.perform(post("/news")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }


    @Test
    @SneakyThrows
    void checkAddNewsShouldReturnResponseEmptyText() {
        String jsonCreateDto = mapper.writeValueAsString(getModificationDtoWithEmptyText());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyNewsText());

        mvc.perform(post("/news")
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddNewsByShouldThrowNoPermissionException() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoPermissionForNewsAdd());
        String jsonCreateDto = mapper.writeValueAsString(getModificationDto());

        mvc.perform(post("/news")
                        .header(HttpHeaders.AUTHORIZATION, getSubscriberToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddNewsShouldReturnResponseForbiddenWhenNoToken() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoAuthHeader());
        String jsonCreateDto = mapper.writeValueAsString(getModificationDto());

        mvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldReturnResponseWithUpdatedField() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDto());
        String jsonModResponse = mapper.writeValueAsString(getNewsUpdatedResponse());

        mvc.perform(patch("/news/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldReturnResponseWithNotFound() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDto());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNewsNotFoundExceptionForUpdate());

        mvc.perform(patch("/news/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldReturnResponseEmptyText() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDtoWithEmptyText());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyNewsText());

        mvc.perform(patch("/news/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldReturnResponseEmptyTitle() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDtoWithEmptyTitle());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyNewsTitle());

        mvc.perform(patch("/news/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldReturnResponseNotOwner() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDto());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNotOwnerNewsForUpdate());

        mvc.perform(patch("/news/{id}", 3L)
                        .header(HttpHeaders.AUTHORIZATION, getJournalistToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsShouldThrowNoPermissionException() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDto());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoPermissionForNewsUpdate());

        mvc.perform(patch("/news/{id}", 3L)
                        .header(HttpHeaders.AUTHORIZATION, getSubscriberToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateNewsByIdShouldReturnResponseNotCorrectId() {
        String jsonUpdateDto = mapper.writeValueAsString(getModificationDto());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseNotCorrectId());

        mvc.perform(patch("/news/{id}", -1L)
                        .header(HttpHeaders.AUTHORIZATION, getSubscriber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteNewsByIdShouldReturnResponseWithDeletedId() {
        String jsonModResponse = mapper.writeValueAsString(getNewsDeletedResponse());

        mvc.perform(delete("/news/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteNewsByIdShouldTReturnResponseNotFound() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNewsNotFoundExceptionForDelete());

        mvc.perform(delete("/news/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteNewsByIdShouldReturnResponseNotOwner() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNotOwnerNewsForDelete());

        mvc.perform(delete("/news/{id}", 3L)
                        .header(HttpHeaders.AUTHORIZATION, getJournalistToken()))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteNewsByIdShouldReturnResponseNoPermission() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithNoPermissionForNewsDelete());

        mvc.perform(delete("/news/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, getSubscriberToken()))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteNewsByIdShouldReturnResponseNotCorrectId() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseNotCorrectId());

        mvc.perform(delete("/news/{id}", -1L)
                        .header(HttpHeaders.AUTHORIZATION, getAdminToken()))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }
}