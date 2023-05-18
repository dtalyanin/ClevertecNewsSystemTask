package ru.clevertec.news.models;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId();
}