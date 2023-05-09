package ru.clevertec.nms.utils.cache;

/**
 * Interface for performing operations with cache
 *
 * @param <T> type that cache should work with
 */
public interface Cache<T> {
    /**
     * Get value with the specified ID from cache if exist or else return null
     *
     * @param key value ID to search
     * @return value - if a value with the specified ID exists or else null
     */
    T get(int key);

    /**
     * Put value in cache with the specified ID
     *
     * @param key   value ID to add
     * @param value value to add
     */
    void put(int key, T value);

    /**
     * Delete value from cache with the specified ID
     *
     * @param key value ID to delete
     */
    void delete(int key);
}
