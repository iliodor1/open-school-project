package ru.eldar.configuration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import ru.eldar.annotation.Bean;
import ru.eldar.annotation.Configuration;
import ru.eldar.exception.ContextException;
import ru.eldar.proxy.ProxyApplier;

public class ApplicationContext {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContext(String path) {
        Reflections reflections = new Reflections(path);
        init(reflections);
    }

    protected void init(Reflections reflections) {
        List<?> configurations = getConfigurations(reflections);
        createBeans(configurations);
    }

    private void wrapProxies(Class<?> type, Object bean) {
        ProxyApplier instance = getInstance(ProxyApplier.class);
        Object proxy = instance.apply(bean);
        beans.put(type, proxy);
    }

    public <T> T getInstance(Class<T> clazz) {
        return (T) Optional.ofNullable(beans.get(clazz))
                           .orElseThrow(
                                   () -> new ContextException("Failed to get bean from beans map" + clazz.getTypeName())
                           );
    }

    public List<Object> getAllInstances() {
        return beans.values().stream().toList();
    }

    private List<?> getConfigurations(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Configuration.class)
                          .stream()
                          .map(t -> {
                                            try {
                                                return t.getDeclaredConstructor().newInstance();
                                            } catch (Exception e) {
                                                throw new ContextException("Failed to create config " + t.getTypeName(), e);
                                            }
                                        }).toList();
    }

    private void createBeans(List<?> configurations) {
        for (Object configuration : configurations) {
            var methods = Arrays.stream(configuration.getClass().getMethods())
                                .filter(method -> method.isAnnotationPresent(Bean.class))
                                .collect(Collectors.toCollection(LinkedList::new));
            while (!methods.isEmpty()) {
                Method method = methods.poll();
                if (method.getParameterCount() == 0) {
                    try {
                        Object object = method.invoke(configuration);
                        beans.put(method.getReturnType(), object);
                    } catch (Exception e) {
                        throw new ContextException("Failed to create bean by " + method.getName(), e);
                    }
                } else {
                    List<Object> collect = Arrays.stream(method.getParameterTypes())
                                                 .map(beans::get).toList();
                    if (collect.contains(null)) {
                        methods.offer(method);
                    } else {
                        try {

                            Object object = method.invoke(configuration, collect.toArray());
                            wrapProxies(method.getReturnType(), object);
                        } catch (Exception e) {
                            throw new ContextException("Failed to create bean by " + method.getName(), e);
                        }
                    }
                }
            }
        }
    }
}
