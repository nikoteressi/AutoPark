package application.infrastructure.core;

import org.reflections.Reflections;

import java.util.Set;

public interface Scanner {
    <T> Set<Class<? extends T>> getSubTypesOF(Class<T> type);
    Reflections getReflections();
}
