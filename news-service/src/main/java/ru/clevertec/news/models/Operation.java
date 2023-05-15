package ru.clevertec.news.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Operation {
    GET("get"),
    ADD("add"),
    UPDATE("update"),
    DELETE("delete");

    private final String name;
}
