package ru.eldar.servlet;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.eldar.service.HelpService;
import ru.eldar.service.HelpServiceImpl;

@WebServlet("/help-service/v1/support")
public class HelpServlet extends HttpServlet {

    private final HelpService helpService = HelpServiceImpl.getInstance();
    private static final Logger log = LogManager.getLogger();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Receive a request to add a message");
        String message;
        try (var reader = request.getReader();
             var lines = reader.lines()) {
            message = lines.collect(Collectors.joining("\n"));
            boolean isValid = isValidMessage(message);

            if (isValid) {
                helpService.addSupportMessage(message);
                log.info("Post request processed successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                log.error("Invalid message: The request received an empty string");
                try (var writer = response.getWriter()) {
                    writer.append("Invalid message: The message must not be empty.");
                }
            }
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Receive a request to get a message");
        response.setContentType("text/plain");
        try (var writer = response.getWriter()) {
            writer.append(helpService.getSupportMessage());
            log.info("Get request processed successfully");
        }
    }

    private boolean isValidMessage(String message) {
        return !message.trim().isBlank();
    }
}
