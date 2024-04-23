package ru.eldar.proxy;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.eldar.annotation.Logging;

public class LoggingProxyApplier implements ProxyApplier {
    private static final Logger log = LogManager.getLogger();

    @Override
    public Object apply(Object object) {
        boolean isAnnotated = Arrays.stream(object.getClass().getInterfaces())
                                    .flatMap(i -> Arrays.stream(i.getMethods()))
                                    .anyMatch(m -> m.isAnnotationPresent(Logging.class));

        if (isAnnotated) {
            return Proxy.newProxyInstance(
                    object.getClass().getClassLoader(),
                    object.getClass().getInterfaces(),
                    (proxy, method, methodArgs) -> {
                        if (method.isAnnotationPresent(Logging.class)) {
                            var annotation = method.getAnnotation(Logging.class);
                            log.info(annotation.before());
                            var result = method.invoke(object, methodArgs);
                            log.info(annotation.after());

                            return result;
                        }

                        return method.invoke(object, methodArgs);
                    });
        }

        return object;
    }
}
