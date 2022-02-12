package application.infrastructure.orm.impl;

import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.orm.ConnectionFactory;
import application.infrastructure.orm.EntityManager;
import application.infrastructure.orm.service.PostgreDataBaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @AutoWired
    private ConnectionFactory connection;
    @AutoWired
    private PostgreDataBaseService dataBaseService;
    @AutoWired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> tClass) {
        Optional<T> object = Optional.empty();
        try {
             object = dataBaseService.get(id, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        List<T> objects = new ArrayList<>();
        try {
            objects = dataBaseService.getAll(clazz);
        } catch (Exception e) {e.printStackTrace();
        }
        return objects;
    }
}
