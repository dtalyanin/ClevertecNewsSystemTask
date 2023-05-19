package ru.clevertec.users.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.users.integration.BaseIntegrationTest;

import static generators.factories.CreateDtoFactory.*;
import static generators.factories.ErrorResponseFactory.*;
import static generators.factories.ModificationResponseFactory.*;
import static generators.factories.TokenFactory.*;
import static generators.factories.UpdateDtoFactory.*;
import static generators.factories.UserDtoFactory.*;
import static generators.factories.ValidationResponseFactory.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class UsersControllerTest extends BaseIntegrationTest {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.lifetime}")
    private long tokenLifetime;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private String token;

    @BeforeEach
    void setUp() {
        token = getBearerCorrectAdminToken(secretKey, tokenLifetime);
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturnAll5Users() {
        String jsonUsers = mapper.writeValueAsString(getAllUserDtos());

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturn2UsersWithPageSize2() {
        String jsonUsers = mapper.writeValueAsString(getFirst2UserDtos());

        mvc.perform(get("/users")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturn2UsersFrom2PageAndSize2() {
        String jsonUsers = mapper.writeValueAsString(getFrom2PageFirst2UserDtos());

        mvc.perform(get("/users")
                        .param("size", "2")
                        .param("page", "2"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        String jsonUsers = mapper.writeValueAsString(getEmptyListUserDtos());

        mvc.perform(get("/users")
                        .param("size", "20")
                        .param("page", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturnAll5UsersWithoutSort() {
        String jsonUsers = mapper.writeValueAsString(getAllUserDtos());

        mvc.perform(get("/users")
                        .param("sort", "username"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturnAll5UsersWhenNegativeSize() {
        String jsonUsers = mapper.writeValueAsString(getAllUserDtos());

        mvc.perform(get("/users")
                        .param("size", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @SneakyThrows
    void checkGetAllUsersWithPaginationShouldReturnAll5UsersWhenNegativePage() {
        String jsonUsers = mapper.writeValueAsString(getAllUserDtos());

        mvc.perform(get("/users")
                        .param("page", "-1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUsers))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @SneakyThrows
    void checkGetUserByIdShouldReturnExistingUser() {
        String jsonUser = mapper.writeValueAsString(getSubscriberDto());

        mvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    @SneakyThrows
    void checkGetUserByIdShouldThrowNotFoundException() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithUserNotFoundException());

        mvc.perform(get("/users/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkGetUserByTokenShouldReturnUser() {
        String jsonUser = mapper.writeValueAsString(getSubscriberDto());
        token = getCorrectSubscriberToken(secretKey, tokenLifetime);

        mvc.perform(get("/users/token/{token}", token))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonUser));
    }

    @Test
    @SneakyThrows
    void checkGetUserByTokenShouldThrowTokenExceptionWhenNoUserFound() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithTokenNotValid());
        token = getTokenWithNotExistingUsername(secretKey, tokenLifetime);

        mvc.perform(get("/users/token/{token}", token))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkGetUserByTokenShouldThrowTokenExceptionWhenTokenExpired() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithTokenExceptionTokenExpired());
        token = getTokenWithExpiredDate(secretKey, tokenLifetime);

        mvc.perform(get("/users/token/{token}", token))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkGetUserByTokenShouldThrowTokenExceptionWhenTokenNotCorrect() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithIncorrectToken());

        mvc.perform(get("/users/token/{token}", token))
                .andExpect(status().isForbidden())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldReturnModificationResponseWithGeneratedId() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDto());
        String jsonModResponse = mapper.writeValueAsString(getUserAddedResponse());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonModResponse))
                .andExpect(header().string(HttpHeaders.LOCATION, containsString("users/6")));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldThrowExceptionUsernameExist() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithExistedInDbName());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseUsernameAlreadyExist());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldThrowExceptionEmptyUsername() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithEmptyName());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyUsername());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldThrowExceptionTooBigUsername() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithTooBigName());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseTooBigUsername());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldThrowExceptionEmptyPassword() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithEmptyPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyPassword());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserShouldThrowExceptionTooSmallPassword() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDtoWithTooSmallPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseTooSmallPassword());

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkAddUserIdShouldForbiddenWhenNoToken() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDto());

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkAddUserIdShouldForbiddenWhenNotAdmin() {
        String jsonCreateDto = mapper.writeValueAsString(getCreateDto());
        token = getBearerCorrectSubscriberToken(secretKey, tokenLifetime);

        mvc.perform(post("/users")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldReturnResponseWithUpdatedId() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());
        String jsonModResponse = mapper.writeValueAsString(getUserUpdatedResponse());

        mvc.perform(patch("/users/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldThrowNotFoundException() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDto());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithUserNotFoundExceptionForUpdate());

        mvc.perform(patch("/users/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldThrowExceptionEmptyPassword() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDtoWithEmptyPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseEmptyPassword());

        mvc.perform(patch("/users/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldThrowExceptionTooSmallPassword() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDtoWithTooSmallPassword());
        String jsonErrorResponse = mapper.writeValueAsString(getResponseTooSmallPassword());

        mvc.perform(patch("/users/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldForbiddenWhenNoToken() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDtoWithTooSmallPassword());

        mvc.perform(patch("/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkUpdateUserShouldForbiddenWhenNotAdmin() {
        String jsonUpdateDto = mapper.writeValueAsString(getUpdateDtoWithTooSmallPassword());
        token = getCorrectSubscriberToken(secretKey, tokenLifetime);

        mvc.perform(patch("/users/{id}", 1L)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdateDto))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkDeleteUserByIdShouldReturnResponseWithDeletedId() {
        String jsonModResponse = mapper.writeValueAsString(getUserDeletedResponse());

        mvc.perform(delete("/users/{id}", 5L)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonModResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteUserByIdShouldThrowNotCorrectId() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseNotCorrectId());

        mvc.perform(delete("/users/{id}", -1L)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteUserByIdShouldThrowNotFoundException() {
        String jsonErrorResponse = mapper.writeValueAsString(getResponseWithUserNotFoundExceptionForDelete());

        mvc.perform(delete("/users/{id}", 100L)
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isNotFound())
                .andExpect(content().json(jsonErrorResponse));
    }

    @Test
    @SneakyThrows
    void checkDeleteUserByIdShouldForbiddenWhenNoToken() {
        mvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkDeleteUserByIdShouldForbiddenWhenNotAdmin() {
        token = getCorrectSubscriberToken(secretKey, tokenLifetime);

        mvc.perform(delete("/users/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden());
    }
}