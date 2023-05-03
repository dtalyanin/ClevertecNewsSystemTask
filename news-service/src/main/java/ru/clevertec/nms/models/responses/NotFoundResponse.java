package ru.clevertec.nms.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotFoundResponse {
    private long incorrectId;
    private String errorMessage;
    private int errorCode;
}
