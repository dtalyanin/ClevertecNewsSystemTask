package ru.clevertec.nms.utils.cache.impl;

import ru.clevertec.exceptions.exceptions.CacheException;
import ru.clevertec.nms.utils.cache.Cache;

import java.util.HashMap;
import java.util.Map;

import static ru.clevertec.nms.utils.constants.MessageConstants.WRONG_CACHE_CAPACITY;

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
            moveToHead(current);
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
     * Add node to the head of queue
     *
     * @param node node to move
     */
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

        @Override
        public String toString() {
            return value.toString();
        }
    }

    @Override
    public String toString() {
        return "LFUCache: " + elements;
    }
}
