package ru.clevertec.nms.models;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId();
}