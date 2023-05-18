package ru.clevertec.users.models;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId();
}