package ru.clevertec.nms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.nms.utils.cache.Cache;
import ru.clevertec.nms.utils.cache.impl.LFUCache;
import ru.clevertec.nms.utils.cache.impl.LRUCache;


@Component
public class CacheFactory {
    @Value("${cache.type}")
    private static String cacheChoice;

    public static <T> Cache<T> getCacheImplementation(Class<T> tClass) {
        return switch (cacheChoice) {
            case "lru" -> new LRUCache<>(10);
            case "lfu" -> new LFUCache<>(10);
            default -> throw new IllegalArgumentException("aaa");
        };
    }
}