package application.infrastructure.config.impl;

import application.infrastructure.config.Config;
import application.infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private  final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> types = scanner.getSubTypesOF(target);
        if (types.size() == 1) return (Class<? extends T>) types.toArray()[0];
        if (types.size() > 1 && !interfaceToImplementation.containsKey(target)) {
            throw new RuntimeException("Target interface has 0 or more then one implementations.");
        } else {
            return (Class<? extends T>) interfaceToImplementation.get(target);
        }
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
