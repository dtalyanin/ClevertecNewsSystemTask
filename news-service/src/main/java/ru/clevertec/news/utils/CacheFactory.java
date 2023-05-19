package ru.clevertec.news.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.news.utils.cache.Cache;
import ru.clevertec.news.utils.cache.impl.LFUCache;
import ru.clevertec.news.utils.cache.impl.LRUCache;

import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;
import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CHOICE;

/**
 * Factory for getting cache implementation
 */
@Component
@Profile("dev")
public class CacheFactory {

    private final String implementation;
    private final int capacity;

    public CacheFactory(@Value("${cache.type}") String implementation, @Value("#${cache.capacity}") int capacity) {
        this.implementation = implementation;
        this.capacity = capacity;
    }

    /**
     * returns a cache based on values from application.yml
     *
     * @return cache implementation
     */
    public <T> Cache<T> getCacheImplementation() {
        if (capacity <= 0) {
            throw new CacheException(WRONG_CACHE_CAPACITY);
        }
        String lowerCaseImplementation = implementation.toLowerCase();
        return switch (lowerCaseImplementation) {
            case "lru" -> new LRUCache<>(capacity);
            case "lfu" -> new LFUCache<>(capacity);
            default -> throw new CacheException(WRONG_CACHE_CHOICE);
        };
    }
}