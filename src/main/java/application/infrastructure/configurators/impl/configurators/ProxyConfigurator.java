package application.infrastructure.configurators.impl.configurators;

import application.infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implemenation, Context context);
}
