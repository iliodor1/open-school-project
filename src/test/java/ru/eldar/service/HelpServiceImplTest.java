package ru.eldar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.eldar.dao.HelpMessageDao;

@ExtendWith(MockitoExtension.class)
class HelpServiceImplTest {
    @InjectMocks
    HelpServiceImpl service;

    @Mock
    HelpMessageDao helpMessageDao;

    @Test
    void addSupportMessage() {
        service.addSupportMessage(anyString());
        verify(helpMessageDao, times(1)).addMessage(anyString());
    }

    @Test
    void whenGetSupportMessage_thenReturnRandomMessage() {
        var returnedMessage = List.of("some message");
        when(helpMessageDao.getAll()).thenReturn(returnedMessage);
        String supportMessage = service.getSupportMessage();
        verify(helpMessageDao, times(1)).getAll();
        assertEquals("some message", supportMessage);
    }

    @Test
    void whenGetEmptySupportMessages_thenReturnDefaultMessage() {
        var defaultMessage = "You are amazing and you know it!";
        when(helpMessageDao.getAll()).thenReturn(Collections.emptyList());
        String supportMessage = service.getSupportMessage();
        verify(helpMessageDao, times(1)).getAll();
        assertEquals(defaultMessage, supportMessage);
    }
}