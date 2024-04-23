package ru.eldar.dao;

import java.util.ArrayList;
import java.util.List;

public class HelpMessageMemoryDao implements HelpMessageDao {
    private final List<String> supportMessages = new ArrayList<>();

    @Override
    public List<String> getAll() {
        return supportMessages;
    }

    @Override
    public void addMessage(String message) {
        supportMessages.add(message);
    }
}
