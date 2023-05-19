package ru.clevertec.news.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Operation types with entities
 */
@RequiredArgsConstructor
@Getter
public enum Operation {
    GET("get"),
    ADD("add"),
    UPDATE("update"),
    DELETE("delete");

    private final String name;
}
