package ru.eldar.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FrontControllerTest {

    StringWriter writer;

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @BeforeEach
    void beforeMethod() {
        writer = new StringWriter();
    }

    @Test
    void whenAddBlankMessage_thenErrorMessage() throws IOException, ServletException {
        var frontController = new FrontController();
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(" ")));
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/help/v1/support");
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        frontController.init();
        frontController.doPost(request, response);

        assertEquals(writer.toString(), "{\"Error message\" : \"Invalid message: The message must not be empty.\"}");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void whenAddMessage() throws IOException, ServletException {
        var frontController = new FrontController();
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("{\"message\":\"Support message\"}")));
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("/help/v1/support");

        frontController.init();
        frontController.doPost(request, response);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
    }


    @Test
    void whenGetMessage_thenReturnsMessage() throws IOException, ServletException {
        var frontController = new FrontController();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/help/v1/support");

        frontController.init();
        frontController.doGet(request, response);

        assertEquals(writer.toString(), "{\"message\":\"You are amazing and you know it!\"}");
        verify(response, times(1)).setContentType("application/json");
    }
}