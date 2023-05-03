package ru.clevertec.uas.models;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId();
}