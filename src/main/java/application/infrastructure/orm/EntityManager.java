package application.infrastructure.orm;

import java.util.List;
import java.util.Optional;

public interface EntityManager {
    <T> Optional<T> get(Long id, Class<T> tClass);
    Long save(Object object);
    <T> List<T> getAll(Class<T> clazz);
}
