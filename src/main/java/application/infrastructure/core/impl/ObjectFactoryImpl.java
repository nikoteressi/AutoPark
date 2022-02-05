package application.infrastructure.core.impl;

import application.infrastructure.configurators.impl.ObjectConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.core.ObjectFactory;
import application.infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> sets = context.getConfig().getScanner().getSubTypesOF(ObjectConfigurator.class);
        for (Class<?> c: sets ) {
            objectConfigurators.add((ObjectConfigurator) c.newInstance());
        }
    }
    private  <T> T create(Class<T> implementation) throws  Exception{
        return implementation.newInstance();
    }
    private <T> void configure (T object) {
        for (ObjectConfigurator o : objectConfigurators) {
            o.configure(object, context);
        }
    }
    private <T> void initialize(Class<T> implementation, T object) throws Exception{
        for (Method m : implementation.getDeclaredMethods()) {
            if (m.isAnnotationPresent(InitMethod.class)) {
                m.setAccessible(true);
                m.invoke(object);
            }
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        return object;
    }
}
