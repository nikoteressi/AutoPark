package application.infrastructure.core.impl;

import application.infrastructure.configurators.impl.configurators.ObjectConfigurator;
import application.infrastructure.configurators.impl.configurators.ProxyConfigurator;
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
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> sets = context.getConfig().getScanner().getSubTypesOF(ObjectConfigurator.class);
        for (Class<?> c: sets ) {
            objectConfigurators.add((ObjectConfigurator) c.getDeclaredConstructor().newInstance());
        }
        Set<Class<? extends ProxyConfigurator>> proxies = context.getConfig().getScanner().getSubTypesOF(ProxyConfigurator.class);
        for (Class<? extends ProxyConfigurator> p : proxies) {
            proxyConfigurators.add(p.getDeclaredConstructor().newInstance());
        }
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator p : proxyConfigurators) {
            object = (T) p.makeProxy(object, implClass, context);
        }
        return object;
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
        object = makeProxy(implementation, object);
        return object;
    }
}
