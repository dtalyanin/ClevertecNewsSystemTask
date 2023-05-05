package ru.clevertec.nms.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundResponse {
    private Object incorrectValue;
    private String errorMessage;
    private int errorCode;
}
