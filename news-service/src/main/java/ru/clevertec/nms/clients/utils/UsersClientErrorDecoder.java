package ru.clevertec.nms.clients.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.clevertec.exceptions.exceptions.UsersClientException;
import ru.clevertec.exceptions.models.ErrorResponse;

@Component
@RequiredArgsConstructor
public class UsersClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper;

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        return new UsersClientException(errorResponse.getErrorMessage(), errorResponse.getErrorCode(), responseStatus);
    }
}
