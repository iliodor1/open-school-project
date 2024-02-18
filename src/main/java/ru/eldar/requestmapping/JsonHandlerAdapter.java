package ru.eldar.requestmapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.eldar.constant.RequestMethod;
import ru.eldar.dto.MessageDto;
import ru.eldar.mapper.JsonMapper;


public class JsonHandlerAdapter implements HandlerAdapter {

    private JsonMapper jsonMapper;

    public JsonHandlerAdapter(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, MappingInfo mappingInfo) {
        if (RequestMethod.POST.toString().equals(request.getMethod())) {
            try (var reader = request.getReader();
                 var lines = reader.lines()) {
                var message = lines.collect(Collectors.joining());

                if (isValidMessage(message)) {
                    var methodParameters = Arrays.stream(mappingInfo.getParameters())
                                                 .map(parameter -> jsonMapper.mapToObject(message, parameter.getType()))
                                                 .toArray();

                    mappingInfo.getMethod().invoke(mappingInfo.getController(), methodParameters);

                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    try (var writer = response.getWriter()) {
                        writer.append("{\"Error message\" : \"Invalid message: The message must not be empty.\"}");
                    }
                }
            } catch (IOException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else if (RequestMethod.GET.toString().equals(request.getMethod())) {
            var instance = mappingInfo.getController();
            var method = mappingInfo.getMethod();

            try (var writer = response.getWriter()) {
                MessageDto body = (MessageDto) method.invoke(instance);
                writer.append(jsonMapper.mapToJson(body));
            } catch (IOException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean isValidMessage(String message) {
        return !message.trim().isBlank();
    }
}
