package ru.eldar.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.eldar.configuration.ApplicationContext;
import ru.eldar.requestmapping.AnnotationHandlerMapping;
import ru.eldar.requestmapping.HandlerAdapter;
import ru.eldar.requestmapping.HandlerMapping;
import ru.eldar.requestmapping.Pair;

@WebServlet("/*")
public class FrontController extends HttpServlet {
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;


    @Override
    public void init() throws ServletException {
        super.init();
        ApplicationContext context = new ApplicationContext("ru.eldar");
        handlerMapping = new AnnotationHandlerMapping(context);
        handlerAdapter = context.getInstance(HandlerAdapter.class);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        var controllerInfo = handlerMapping.getMapping(Pair.of(request.getMethod(), request.getRequestURI()));
        handlerAdapter.handle(request, response, controllerInfo);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        var controllerInfo = handlerMapping.getMapping(Pair.of(request.getMethod(), request.getRequestURI()));
        handlerAdapter.handle(request, response, controllerInfo);
    }
}