package ru.clevertec.news.utils.cache.impl;

import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.news.utils.cache.Cache;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.news.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;

/**
 * Least Recently Used (LRU) cache algorithm remove the least recently used frame when the cache is full
 * and a new page is referenced which is not there in the cache. Please see the Galvin book for more details
 *
 * @param <T> type that cache should work with
 */
public class LRUCache<T> implements Cache<T> {

    private final long capacity;
    private final Map<Long, Node<T>> elements;
    private final Node<T> head;
    private final Node<T> tail;

    public LRUCache(long capacity) {
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

    @Override
    public T get(long key) {
        Node<T> current = elements.get(key);
        T value = null;
        if (current != null) {
            moveToHead(current);
            value = current.value;
        }
        return value;
    }

    @Override
    public void put(long key, T value) {
        if (elements.containsKey(key)) {
            Node<T> current = elements.get(key);
            current.value = value;
            moveToHead(current);
        } else {
            if (elements.size() == capacity) {
                elements.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node<T> node = new Node<>(key, value);
            elements.put(key, node);
            addToHead(node);
        }
    }

    @Override
    public void delete(long key) {
        Node<T> removed = elements.remove(key);
        if (removed != null) {
            remove(removed);
        }
    }

    private void addToHead(Node<T> node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    /**
     * Change node position to the head of queue
     *
     * @param node node to move
     */
    private void moveToHead(Node<T> node) {
        remove(node);
        addToHead(node);
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
     * Cache element that stores ID, value, previous ana next element in cache
     *
     * @param <T> type that node should work with
     */
    private static class Node<T> {
        long key;
        T value;
        Node<T> prev;
        Node<T> next;

        public Node() {
        }

        public Node(long key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
