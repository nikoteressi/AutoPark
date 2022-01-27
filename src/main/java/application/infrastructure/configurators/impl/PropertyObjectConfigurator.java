package application.infrastructure.configurators.impl;

import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.Proprety;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertyObjectConfigurator implements ObjectConfigurator{
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if (path == null) throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object object, Context context) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Proprety.class)) {
                Annotation annotation = f.getAnnotation(Proprety.class);
                Proprety prop = (Proprety) annotation;
                if (prop.value().equals("")) {
                    f.set(object, object.getClass().getSimpleName());
                } else {
                    f.set(object, properties.get(prop.value()));
                }
            }
        }
    }
}
