package ru.clevertec.news.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModificationResponse {
    private long id;
    private String message;
}
