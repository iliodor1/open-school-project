package ru.eldar.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eldar.dto.MessageDto;
import ru.eldar.service.HelpService;

@ExtendWith(MockitoExtension.class)
class HelpControllerTest {

    @InjectMocks
    HelpController controller;

    @Mock
    HelpService helpService;

    @Test
    void whenAddMessage_shouldInvokeAddSupportMessageMethod() {
        var messageDto = MessageDto.builder()
                                   .message("Some message")
                                   .build();

        controller.addMessage(messageDto);

        verify(helpService, times(1)).addSupportMessage(any());
    }

    @Test
    void whetGetMessage_shouldReturnMessageDto() {
        var messageDto = MessageDto.builder()
                                   .message("Some message")
                                   .build();

        when(helpService.getSupportMessage()).thenReturn("Some message");

        var responseMessageDto = controller.getMessage();

        assertEquals(messageDto.getMessage(), responseMessageDto.getMessage());
        verify(helpService, times(1)).getSupportMessage();
    }
}