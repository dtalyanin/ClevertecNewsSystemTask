package ru.clevertec.news.clients.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.clevertec.exceptions.exceptions.UsersClientException;
import ru.clevertec.exceptions.models.ErrorResponse;

/**
 * Decoder for managing exception thrown by external client
 */
@Component
@RequiredArgsConstructor
public class UsersClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;

    /**
     * @param methodKey key of the java method that invoked the request.
     * @param response  HTTP response where status is greater than or equal to {@code 300}.
     * @return Custom exception representation
     */
    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        return new UsersClientException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(), responseStatus);
    }
}
