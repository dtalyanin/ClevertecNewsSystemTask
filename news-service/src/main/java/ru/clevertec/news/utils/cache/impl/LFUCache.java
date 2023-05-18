package ru.clevertec.news.utils.cache.impl;

import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.news.utils.cache.Cache;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;

/**
 * Least Frequently Used (LFU) cache algorithm that keeps track of the number of times an item has been accessed.
 * When the cache is full and requires more room the system will purge the item with the lowest reference frequency.
 *
 * @param <T> type that cache should work with
 */

public class LFUCache<T> implements Cache<T> {

    private final int capacity;
    private final Map<Long, Node<T>> elements;
    private final Node<T> head;
    private final Node<T> tail;

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new CacheException(WRONG_CACHE_CAPACITY);
        }

        this.capacity = capacity;
        this.elements = new HashMap<>();

        this.head = new Node<>();
        this.tail = new Node<>();

        head.next = tail;
        tail.prev = head;
    }

    /**
     * Get value with the specified ID from cache if exist or else return null
     *
     * @param key value ID to search
     * @return value - if a value with the specified ID exists or else null
     */
    @Override
    public T get(long key) {
        Node<T> current = elements.get(key);
        T value = null;
        if (current != null) {
            changeCurrentPosition(current);
            value = current.value;
        }
        return value;
    }

    /**
     * Put value in cache with the specified ID
     *
     * @param key   value ID to add
     * @param value value to add
     */
    @Override
    public void put(long key, T value) {
        if (elements.containsKey(key)) {
            Node<T> current = elements.get(key);
            current.value = value;
            changeCurrentPosition(current);
        } else {
            if (elements.size() == capacity) {
                elements.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node<T> node = new Node<>(key, value);
            elements.put(key, node);
            moveByFrequency(node);
        }
    }

    /**
     * Delete value from cache with the specified ID
     *
     * @param key value ID to delete
     */
    @Override
    public void delete(long key) {
        Node<T> removed = elements.remove(key);
        if (removed != null) {
            remove(removed);
        }
    }

    /**
     * Remove the specified node and change links of neighboring nodes
     *
     * @param node node to remove
     */
    private void remove(Node<T> node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    /**
     * Change the specified node position in queue
     *
     * @param current node to remove
     */
    private void changeCurrentPosition(Node<T> current) {
        remove(current);
        current.count++;
        moveByFrequency(current);
    }

    /**
     * Change the specified node position based on the access frequency
     *
     * @param node node to remove
     */
    private void moveByFrequency(Node<T> node) {
        Node<T> curr = head.next;
        while (curr != null) {
            if (curr.count > node.count) {
                curr = curr.next;
            } else {
                node.prev = curr.prev;
                node.next = curr;
                node.next.prev = node;
                node.prev.next = node;
                break;
            }
        }
    }

    /**
     * Cache element that stores ID, value, previous ana next element in cache
     *
     * @param <T> type that node should work with
     */
    private static class Node<T> {
        long key;
        T value;
        int count;
        Node<T> prev;
        Node<T> next;

        public Node() {
        }

        public Node(long key, T value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }
}
