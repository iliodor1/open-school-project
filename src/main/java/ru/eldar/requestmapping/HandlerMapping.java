package ru.eldar.requestmapping;

public interface HandlerMapping {

    MappingInfo getMapping(Pair<String, String> methodPathPair);
}
