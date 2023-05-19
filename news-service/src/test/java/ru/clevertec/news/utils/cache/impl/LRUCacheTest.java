package ru.clevertec.news.utils.cache.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.exceptions.exceptions.CacheException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;

class LRUCacheTest {

    private LRUCache<Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(3);
    }

    @Test
    void checkCreatingCacheShouldThrowExceptionWithWrongCapacity() {
        assertThatThrownBy(() -> new LRUCache<Integer>(0))
                .isInstanceOf(CacheException.class)
                .hasMessage(WRONG_CACHE_CAPACITY);
    }

    @Test
    void checkGetShouldReturnValueIfKeyPresent() {
        cache.put(1L, 111);

        assertThat(cache.get(1L)).isEqualTo(111);
    }

    @Test
    void checkGetShouldReturnNullIfKeyNotPresent() {
        cache.put(1L, 111);

        assertThat(cache.get(2L)).isNull();
    }

    @Test
    void checkGetShouldReturnNullIfCacheEmpty() {
        assertThat(cache.get(1L)).isNull();
    }

    @Test
    void checkPutShouldAddAllValuesWithinCapacity() {
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.put(3L, 333);

        assertAll(
                () -> assertThat(cache.get(1L)).isEqualTo(111),
                () -> assertThat(cache.get(2L)).isEqualTo(222),
                () -> assertThat(cache.get(3L)).isEqualTo(333));
    }

    @Test
    void checkPutShouldAddValuesAndRemoveFirstAdded() {
        //should remove 1 and 2, because they were added first
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.put(3L, 333);
        cache.put(4L, 444);
        cache.put(5L, 555);

        assertAll(
                () -> assertThat(cache.get(1L)).isNull(),
                () -> assertThat(cache.get(2L)).isNull(),
                () -> assertThat(cache.get(3L)).isEqualTo(333),
                () -> assertThat(cache.get(4L)).isEqualTo(444),
                () -> assertThat(cache.get(5L)).isEqualTo(555));
    }

    @Test
    void checkPutShouldAddValuesAndUpdateFrequencyWhenUpdateKeyValues() {
        //should remove 3, because 1 and 2 used by updating values
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.put(3L, 333);
        cache.put(1L, 1111);
        cache.put(2L, 2222);
        cache.put(4L, 444);

        assertAll(
                () -> assertThat(cache.get(3L)).isNull(),
                () -> assertThat(cache.get(1L)).isEqualTo(1111),
                () -> assertThat(cache.get(2L)).isEqualTo(2222),
                () -> assertThat(cache.get(4L)).isEqualTo(444));
    }

    @Test
    void checkPutShouldAddValuesAndUpdateFrequencyWhenCallGetMethod() {
        //should remove 2, because 1 updated when call method get
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.get(1L);
        cache.put(3L, 333);
        cache.put(4L, 444);

        assertAll(
                () -> assertThat(cache.get(2L)).isNull(),
                () -> assertThat(cache.get(1L)).isEqualTo(111),
                () -> assertThat(cache.get(3L)).isEqualTo(333),
                () -> assertThat(cache.get(4L)).isEqualTo(444));
    }

    @Test
    void checkDeleteShouldDeleteExistingKey() {
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.put(3L, 333);
        cache.delete(2);

        assertAll(
                () -> assertThat(cache.get(2L)).isNull(),
                () -> assertThat(cache.get(1L)).isEqualTo(111),
                () -> assertThat(cache.get(3L)).isEqualTo(333));
    }

    @Test
    void checkDeleteShouldDoNothingWithNotPresentKey() {
        cache.put(1L, 111);
        cache.put(2L, 222);
        cache.put(3L, 333);

        assertAll(
                () -> assertThatCode(() -> cache.delete(4L)).doesNotThrowAnyException(),
                () -> assertThat(cache.get(1L)).isEqualTo(111),
                () -> assertThat(cache.get(2L)).isEqualTo(222),
                () -> assertThat(cache.get(3L)).isEqualTo(333));
    }
}