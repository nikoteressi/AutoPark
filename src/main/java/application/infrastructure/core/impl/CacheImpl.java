package application.infrastructure.core.impl;

import application.infrastructure.core.Cache;

import java.util.*;

public class CacheImpl implements Cache {
    private final Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> tClass) {
        return contains(tClass) ? (T) cache.get(tClass.getName()) : null;
    }

    @Override
    public <T> void put(Class<?> clazz, T value) {
        cache.put(clazz.getName(), value);
    }
}
