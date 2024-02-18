package ru.eldar.requestmapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MappingInfo {
    private final Object controller;
    private final Method method;

    private final Parameter[] parameters;

    private MappingInfo(Object controller, Method method, Parameter[] parameters) {
        this.controller = controller;
        this.method = method;
        this.parameters = parameters;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public static MappingInfoBuilder builder() {
        return new MappingInfoBuilder();
    }

    static class MappingInfoBuilder {
        private Object controller;
        private Method method;
        private Parameter[] parameters;

        public MappingInfoBuilder controller(Object controller) {
            this.controller = controller;
            return this;
        }

        public MappingInfoBuilder method(Method method) {
            this.method = method;
            return this;
        }

        public MappingInfoBuilder parameters(Parameter[] parameters) {
            this.parameters = parameters;
            return this;
        }

        public MappingInfo build() {
            return new MappingInfo(controller, method, parameters);
        }
    }
}
