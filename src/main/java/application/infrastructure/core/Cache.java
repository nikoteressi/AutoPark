package application.infrastructure.core;

public interface Cache {
    boolean contains(Class<?> clazz);
    <T> T get(Class<T> tClass);
    <T> void  put (Class<?> clazz, T value);
}
