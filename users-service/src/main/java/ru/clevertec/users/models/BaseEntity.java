package ru.clevertec.users.models;

import java.io.Serializable;

/**
 * Interface that guarantees that entity can be stored in DB
 * @param <T> param that must be serializable and can be stored in DB
 */
public interface BaseEntity<T extends Serializable> {
    T getId();
}