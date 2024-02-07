package ru.eldar.dao;

import java.util.ArrayList;
import java.util.List;

public class HelpMessageMemoryDao implements HelpMessageDao {
    private static HelpMessageDao instance;

    private final List<String> supportMessages = new ArrayList<>();

    private HelpMessageMemoryDao() {
    }

    public static HelpMessageDao getInstance() {
        if (instance == null) {
            instance = new HelpMessageMemoryDao();
        }
        return instance;
    }

    @Override
    public List<String> getAll() {
        return supportMessages;
    }

    @Override
    public void addMessage(String message) {
        supportMessages.add(message);
    }
}
