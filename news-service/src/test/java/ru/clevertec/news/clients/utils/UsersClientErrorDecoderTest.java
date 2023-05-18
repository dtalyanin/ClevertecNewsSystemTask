package ru.clevertec.news.clients.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import feign.Request;
import feign.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import ru.clevertec.exceptions.exceptions.UsersClientException;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.ErrorResponse;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.news.utils.constants.MessageConstants.NOT_NEWS_OWNER;

class UsersClientErrorDecoderTest {

    private ObjectMapper mapper;
    private UsersClientErrorDecoder decoder;

    @BeforeEach
    void setUp() {
        mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        decoder = new UsersClientErrorDecoder(mapper);
    }

    @Test
    @SneakyThrows
    void checkDecodeShouldReturnErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse(NOT_NEWS_OWNER,
                ErrorCode.NOT_OWNER_FOR_NEWS_MODIFICATION.getCode());
        String jsonResponse = mapper.writeValueAsString(errorResponse);
        Request request = Request.create(
                Request.HttpMethod.GET,
                "",
                Map.of("", Collections.emptyList()),
                Request.Body.empty(),
                null);
        Response response = Response.builder()
                .body(jsonResponse.getBytes())
                .request(request)
                .status(HttpStatus.FORBIDDEN.value())
                .build();

        Exception actualException = decoder.decode(null, response);

        assertThat(actualException)
                .isInstanceOf(UsersClientException.class)
                .hasMessage(NOT_NEWS_OWNER);
    }
}