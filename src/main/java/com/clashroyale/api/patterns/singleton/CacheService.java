package com.clashroyale.api.patterns.singleton;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


 //singleton pattern - in-memory cache (store last data)

@Component
public class CacheService {

    private static CacheService instance;
    private final Map<String, Object> cache;
    private final LoggerService logger = LoggerService.getInstance();

    // private constructor for Singleton pattern
    private CacheService() {
        this.cache = new HashMap<>();
        logger.info("CacheService initialized");
    }

    // get only one singleton instance, if it don't exist creating new
    public static synchronized CacheService getInstance() {
        if (instance == null) {
            instance = new CacheService();
        }
        return instance;
    }

    // we put value into the cache

    public void put(String key, Object value) {
        cache.put(key, value);
        logger.info("Cache updated: " + key);
    }

    //get value from cache return optional contains cache or null if not found
    public Optional<Object> get(String key) {
        Object value = cache.get(key);
        if (value != null) {
            logger.info("Cache hit: " + key);
        } else {
            logger.info("Cache miss: " + key);
        }
        return Optional.ofNullable(value);
    }

    //clear cache key

    public void clear(String key) {
        cache.remove(key);
        logger.info("Cache cleared: " + key);
    }

    //clear all cache
    public void clearAll() {
        cache.clear();
        logger.info("All cache cleared");
    }

    //check if cache contains key
    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    //get cache size
    public int size() {
        return cache.size();
    }
}