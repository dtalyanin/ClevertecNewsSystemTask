package ru.clevertec.nms.utils.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ru.clevertec.nms.dto.comments.CommentDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.utils.CacheFactory;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.nms.utils.constants.CacheConstants.COMMENTS_CACHE;
import static ru.clevertec.nms.utils.constants.CacheConstants.NEWS_CACHE;


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

    public Object get(String source, long key) {
        Cache<?> cache = caches.get(source);
        return cache.get(key);
    }

    public void put(String source, long key, Object value) {
        Cache cache = caches.get(source);
        cache.put(key, value);
    }

    public void delete(String source, long key) {
        Cache<?> cache = caches.get(source);
        cache.delete(key);
    }
}
