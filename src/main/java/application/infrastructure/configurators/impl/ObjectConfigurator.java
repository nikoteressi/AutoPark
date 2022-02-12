package application.infrastructure.configurators.impl;

import application.infrastructure.core.Context;

public interface ObjectConfigurator {
    void configure (Object object, Context context);
}
