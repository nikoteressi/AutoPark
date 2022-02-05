package application.infrastructure.core.impl;

import application.infrastructure.core.Cache;

import java.util.*;

@SuppressWarnings("unchecked")
public class CacheImpl implements Cache {
    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getSimpleName());
    }

    @Override
    public <T> T get(Class<T> tClass) {
        return (T) cache.get(tClass.getSimpleName());
    }

    @Override
    public <T> void put(Class<?> clazz, T value) {
        cache.put(clazz.getSimpleName(), value);
    }
}
