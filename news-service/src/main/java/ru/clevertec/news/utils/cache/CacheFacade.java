package ru.clevertec.news.utils.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.utils.CacheFactory;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.news.utils.constants.CacheConstants.COMMENTS_CACHE;
import static ru.clevertec.news.utils.constants.CacheConstants.NEWS_CACHE;

/**
 * Cache that store news and comments cache implementation. Used cache depends on param, that
 * must be transferred in method
 */
@Component
@Profile("dev")
public class CacheFacade {

    private final Map<String, Cache<?>> caches;

    @Autowired
    public CacheFacade(CacheFactory factory) {
        this.caches = new HashMap<>();
        Cache<NewsDto> newsDtoCache = factory.getCacheImplementation();
        Cache<CommentDto> commentDtoCache = factory.getCacheImplementation();
        caches.put(NEWS_CACHE, newsDtoCache);
        caches.put(COMMENTS_CACHE, commentDtoCache);
    }

    /**
     * Get value with the specified ID from cache if exist or else return null
     * @param source cache type
     * @param key value ID to search
     * @return value - if a value with the specified ID exists or else null
     */
    public Object get(String source, long key) {
        Cache<?> cache = caches.get(source);
        if (cache != null) {
            return cache.get(key);
        } else {
            return null;
        }
    }

    /**
     * Put value in cache with the specified ID
     * @param source cache type
     * @param key value ID to add
     * @param value value to add
     */
    public void put(String source, long key, Object value) {
        Cache cache = caches.get(source);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    /**
     * Delete value from cache with the specified ID
     * @param source cache type
     * @param key value ID to delete
     */
    public void delete(String source, long key) {
        Cache<?> cache = caches.get(source);
        if (cache != null) {
            cache.delete(key);
        }
    }
}
