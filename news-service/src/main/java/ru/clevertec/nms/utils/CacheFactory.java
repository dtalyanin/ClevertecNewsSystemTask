package ru.clevertec.nms.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.nms.utils.cache.Cache;
import ru.clevertec.nms.utils.cache.impl.LFUCache;
import ru.clevertec.nms.utils.cache.impl.LRUCache;

import static ru.clevertec.nms.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;
import static ru.clevertec.nms.utils.constants.MessageConstants.WRONG_CACHE_CHOICE;

@Component
@Profile("dev")
public class CacheFactory {

    @Value("${cache.type}")
    private String implementation;
    @Value("#${cache.capacity}")
    private int capacity;

    public <T> Cache<T> getCacheImplementation() {
        if (capacity <= 0) {
            throw new CacheException(WRONG_CACHE_CAPACITY);
        }
        implementation = implementation.toLowerCase();
        return switch (implementation) {
            case "lru" -> new LRUCache<>(capacity);
            case "lfu" -> new LFUCache<>(capacity);
            default -> throw new CacheException(WRONG_CACHE_CHOICE);
        };
    }
}