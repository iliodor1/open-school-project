package ru.eldar.requestmapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    void handle(HttpServletRequest request, HttpServletResponse response, MappingInfo mappingInfo);
}
