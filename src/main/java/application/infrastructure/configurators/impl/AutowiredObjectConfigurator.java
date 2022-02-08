package application.infrastructure.configurators.impl;

import application.infrastructure.configurators.impl.configurators.ObjectConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.AutoWired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(AutoWired.class)) {
                f.set(object, context.getObject(f.getType()));
            }
        }
    }
}
