package com.sparrows.school.cache.key;

@FunctionalInterface
public interface CacheKeyGenerator<K> {
    String generateKey(K key);
}