package application.infrastructure.configurators.impl;

import application.infrastructure.configurators.impl.configurators.ObjectConfigurator;
import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.Proprety;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
       URL path = this.getClass().getClassLoader().getResource("application.properties");
        if (path == null) throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = new HashMap<>();
        Iterator<String> iterator = lines.iterator();
        while (iterator.hasNext()) {
            String[] strings = iterator.next().split("=");
            properties.put(strings[0], strings[1]);
        }
    }

    @SneakyThrows
    @Override
    public void configure(Object object, Context context) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Proprety.class)) {
                Proprety annotation = f.getAnnotation(Proprety.class);
                if (annotation.value().equals("")) {
                    f.set(object, object.getClass().getSimpleName());
                } else {
                    f.set(object, properties.get(annotation.value()));
                }
            }
        }
    }
}
