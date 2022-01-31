package application.infrastructure.configurators.impl;

import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.AutoWired;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

@NoArgsConstructor
public class AutowiredObjectConfigurator implements ObjectConfigurator{

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(AutoWired.class)) {
                f.setAccessible(true);
                f.set(object, context.getObject(f.getType()));
            }
        }
    }
}
