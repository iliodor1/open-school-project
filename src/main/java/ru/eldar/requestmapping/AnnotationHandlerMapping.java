package ru.eldar.requestmapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.eldar.annotation.Controller;
import ru.eldar.annotation.RequestMapping;
import ru.eldar.configuration.ApplicationContext;

public class AnnotationHandlerMapping implements HandlerMapping {
    private final Map<Pair<String, String>, MappingInfo> controllerInfoMap = new HashMap<>();

    private final ApplicationContext context;

    public AnnotationHandlerMapping(ApplicationContext context) {
        this.context = context;
        init();
    }

    private void init() {
        List<Object> allInstances = context.getAllInstances();
        var controllers = allInstances.stream()
                                      .filter(instance -> instance.getClass().isAnnotationPresent(Controller.class))
                                      .toList();

        for (Object controller : controllers) {
            Arrays.stream(controller.getClass().getMethods())
                  .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                  .forEach(method -> setMapping(controller, method));
        }
    }

    @Override
    public MappingInfo getMapping(Pair<String, String> methodPathPair) {
        return controllerInfoMap.get(methodPathPair);
    }

    private void setMapping(Object controller, Method method) {
        var controllerAnnotation = controller.getClass().getAnnotation(Controller.class);
        var requestMappingAnnotation = method.getAnnotation(RequestMapping.class);
        var controllerInfo = MappingInfo.builder()
                                        .controller(controller)
                                        .method(method)
                                        .parameters(method.getParameters())
                                        .build();

        var fullPath = controllerAnnotation.path().concat(requestMappingAnnotation.path());

        controllerInfoMap.put(Pair.of(requestMappingAnnotation.method().toString(), fullPath), controllerInfo);
    }
}
