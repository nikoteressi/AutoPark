package application.infrastructure.configurators.impl.configurators;

import application.infrastructure.core.Context;

public interface ObjectConfigurator {
    void configure (Object object, Context context);
}
