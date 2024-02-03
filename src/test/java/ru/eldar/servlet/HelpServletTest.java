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
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eldar.service.HelpService;

@ExtendWith(MockitoExtension.class)
class HelpServletTest {
    @InjectMocks
    HelpServlet servlet;

    @Mock
    HelpService service;

    StringWriter writer = new StringWriter();


    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @BeforeEach
    public void setUp() throws Exception {
        Class<?> mockServletClass = Class.forName("ru.eldar.servlet.HelpServlet");
        Field field = mockServletClass.getDeclaredField("helpService");
        field.setAccessible(true);
        field.set(servlet, service);
    }

    @Test
    void whenAddBlankMessage_thenErrorMessage() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(" ")));
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        servlet.doPost(request, response);

        assertEquals(writer.toString(), "Invalid message: The message must not be empty.");
    }

    @Test
    void whenAddMessage() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("Support message")));

        servlet.doPost(request, response);

        verify(service, times(1)).addSupportMessage("Support message");
    }


    @Test
    void whenGetMessage_thenReturnsMessage() throws IOException {
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        when(service.getSupportMessage()).thenReturn("Support message");

        servlet.doGet(request, response);

        verify(response, times(1)).setContentType("text/plain");
        verify(service, times(1)).getSupportMessage();

        assertEquals(writer.toString(), "Support message");
    }
}