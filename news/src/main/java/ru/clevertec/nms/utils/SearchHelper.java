package ru.clevertec.nms.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ru.clevertec.nms.models.BaseEntity;

import java.io.Serializable;

@UtilityClass
public class SearchHelper {
    private static final ExampleMatcher MATCHER = ExampleMatcher
            .matchingAll()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    public <T extends BaseEntity<? extends Serializable>> Example<T> getExample(T exampleValue) {
        return Example.of(exampleValue, MATCHER);
    }
}
