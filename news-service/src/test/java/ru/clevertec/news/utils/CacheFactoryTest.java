package ru.clevertec.news.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.news.utils.cache.impl.LFUCache;
import ru.clevertec.news.utils.cache.impl.LRUCache;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;
import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CHOICE;

class CacheFactoryTest {

    private CacheFactory cacheFactory;

    @ParameterizedTest
    @ValueSource(strings = {"lru", "LRU", "lRu"})
    void checkGetCacheImplementationShouldReturnLRU(String choice) {
        cacheFactory = new CacheFactory(choice, 10);
        Class<?> actualClass = cacheFactory.getCacheImplementation().getClass();
        Class<?> expectedClass = LRUCache.class;

        assertThat(actualClass).isEqualTo(expectedClass);
    }

    @ParameterizedTest
    @ValueSource(strings = {"lfu", "LFU", "lFu"})
    void checkGetCacheImplementationShouldReturnLFU(String choice) {
        cacheFactory = new CacheFactory(choice, 10);
        Class<?> actualClass = cacheFactory.getCacheImplementation().getClass();
        Class<?> expectedClass = LFUCache.class;

        assertThat(actualClass).isEqualTo(expectedClass);
    }

    @Test
    void checkGetCacheImplementationShouldThrowExceptionZeroCapacity() {
        cacheFactory = new CacheFactory("lru", 0);
        assertThatThrownBy(() -> cacheFactory.getCacheImplementation())
                .isInstanceOf(CacheException.class)
                .hasMessage(WRONG_CACHE_CAPACITY);
    }

    @Test
    void checkGetCacheImplementationShouldThrowExceptionUnknownImplementation() {
        cacheFactory = new CacheFactory("lru2", 10);
        assertThatThrownBy(() -> cacheFactory.getCacheImplementation())
                .isInstanceOf(CacheException.class)
                .hasMessage(WRONG_CACHE_CHOICE);
    }
}