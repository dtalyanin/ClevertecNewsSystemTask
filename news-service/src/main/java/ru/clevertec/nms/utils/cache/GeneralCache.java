package ru.clevertec.nms.utils.cache;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.models.BaseEntity;
import ru.clevertec.nms.utils.CacheFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Component
public class GeneralCache {
    private final Map<String, Cache<?>> caches;

    @Autowired
    public GeneralCache() {

        this.caches = new HashMap<>();
        Cache<NewsDto> cacheNews = CacheFactory.getCacheImplementation(NewsDto.class);
        Cache<CommentDto> cacheComment = CacheFactory.getCacheImplementation(CommentDto.class);
        caches.put("news", cacheNews);
        caches.put("comments", cacheComment);
    }

    public Object get(String source, long key) {
        Cache<?> cache = caches.get(source);
        return cache.get(key);
    }

    @SneakyThrows
    void put(String source, long key, Object value) {

        Cache cache = caches.get(source);
        cache.put(key, value);
    }

    void delete(String source, long key) {
        Cache<?> cache = caches.get(source);
        cache.delete(key);
    }

    @Override
    public String toString() {
        return caches.toString();
    }
}
