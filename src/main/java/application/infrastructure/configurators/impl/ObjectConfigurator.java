package application.infrastructure.configurators.impl;

import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.Proprety;

@AutoWired
@Proprety
public interface ObjectConfigurator {
    void configure (Object object, Context context);
}
