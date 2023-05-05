package ru.clevertec.nms.models;

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
