package ru.clevertec.nms.utils.cache;

import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.utils.CacheFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class GeneralCache {
    private final Map<String, Cache<? extends Serializable>> caches;

    public GeneralCache() {
        this.caches = new HashMap<>();
        Cache<NewsDto> cacheNews = CacheFactory.getCacheImplementation(NewsDto.class);
        Cache<CommentDto> cacheComment = CacheFactory.getCacheImplementation(CommentDto.class);
        caches.put("news", cacheNews);
        caches.put("comments", cacheComment);
    }

    public Object get(String source, long key) {
        Cache cache = caches.get(source);
        return cache.get(key);
    }

    void put(String source, long key, Object value) {
        Cache cache = caches.get(source);
        cache.put(key, value);
    }

    void delete(String source, long key) {
        Cache cache = caches.get(source);
        cache.delete(key);
    }
}
