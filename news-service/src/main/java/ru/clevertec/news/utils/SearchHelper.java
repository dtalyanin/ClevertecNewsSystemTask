package ru.clevertec.news.utils;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ru.clevertec.news.models.BaseEntity;

import java.io.Serializable;

public class SearchHelper {

    private static final String ID_COLUMN = "id";

    private static final ExampleMatcher MATCHER = ExampleMatcher
            .matchingAll()
            .withIgnorePaths(ID_COLUMN)
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    public static <T extends BaseEntity<? extends Serializable>> Example<T> getExample(T exampleValue) {
        return Example.of(exampleValue, MATCHER);
    }
}
